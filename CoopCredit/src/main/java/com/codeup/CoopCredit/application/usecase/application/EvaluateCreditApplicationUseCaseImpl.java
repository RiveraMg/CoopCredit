package com.codeup.CoopCredit.application.usecase.application;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.model.RiskEvaluation;
import com.codeup.CoopCredit.domain.model.enums.RiskLevel;
import com.codeup.CoopCredit.domain.ports.in.application.IEvaluateCreditApplicationUseCase;
import com.codeup.CoopCredit.domain.ports.out.ICreditApplicationRepositoryPort;
import com.codeup.CoopCredit.domain.ports.out.IMemberRepositoryPort;
import com.codeup.CoopCredit.domain.ports.out.IRiskCentralPort;
import com.codeup.CoopCredit.domain.ports.out.IRiskEvaluationRepositoryPort;
import com.codeup.CoopCredit.domain.exception.ApplicationNotFoundException;
import com.codeup.CoopCredit.domain.exception.ApplicationNotPendingException;
import com.codeup.CoopCredit.domain.exception.MemberNotFoundException;
import com.codeup.CoopCredit.domain.exception.RiskEvaluationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluateCreditApplicationUseCaseImpl implements IEvaluateCreditApplicationUseCase {

    private final ICreditApplicationRepositoryPort applicationRepository;
    private final IMemberRepositoryPort memberRepository;
    private final IRiskEvaluationRepositoryPort evaluationRepository;
    private final IRiskCentralPort riskCentralPort;

    private static final BigDecimal MAX_DEBT_TO_INCOME_RATIO = new BigDecimal("40.0");

    @Override
    @Transactional
    public RiskEvaluation execute(Long applicationId) {
        log.info("Starting evaluation for application ID: {}", applicationId);

        // 1. Retrieve application
        CreditApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException(applicationId));

        if (!application.isPending()) {
            throw new ApplicationNotPendingException(applicationId, application.getStatus());
        }

        // 2. Retrieve member
        Member member = memberRepository.findById(application.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(application.getMemberId()));

        // 3. Call external Risk Central service
        log.info("Calling Risk Central for document: {}", member.getDocument());
        IRiskCentralPort.RiskCentralResponse riskResponse;

        try {
            riskResponse = riskCentralPort.evaluate(
                    member.getDocument(),
                    application.getRequestedAmount(),
                    application.getTermMonths()
            );
        } catch (Exception e) {
            log.error("Error calling Risk Central service", e);
            throw new RiskEvaluationException("Failed to communicate with Risk Central service", e);
        }

        // 4. Apply internal policies
        boolean meetsSeniority = member.meetsMinimumSeniority();
        boolean meetsMaxAmount = member.canRequestAmount(application.getRequestedAmount());
        BigDecimal debtToIncomeRatio = application.calculateDebtToIncomeRatio(member.getSalary());
        boolean meetsDebtToIncome = debtToIncomeRatio.compareTo(MAX_DEBT_TO_INCOME_RATIO) <= 0;

        // 5. Determine approval
        RiskLevel riskLevel = RiskLevel.valueOf(riskResponse.riskLevel());
        boolean approved = meetsSeniority && meetsMaxAmount && meetsDebtToIncome &&
                (riskLevel == RiskLevel.LOW || riskLevel == RiskLevel.MEDIUM);

        // 6. Create evaluation
        RiskEvaluation evaluation = RiskEvaluation.builder()
                .applicationId(applicationId)
                .score(riskResponse.score())
                .riskLevel(riskLevel)
                .debtToIncomeRatio(debtToIncomeRatio)
                .meetsSeniorityRequirement(meetsSeniority)
                .meetsMaximumAmount(meetsMaxAmount)
                .approved(approved)
                .details(riskResponse.details())
                .evaluationDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        if (!approved) {
            evaluation.setRejectionReason(evaluation.buildRejectionReason());
        }

        // 7. Update application status
        if (approved) {
            application.approve();
        } else {
            application.reject();
        }
        application.setUpdatedAt(LocalDateTime.now());
        applicationRepository.save(application);

        // 8. Save evaluation
        RiskEvaluation savedEvaluation = evaluationRepository.save(evaluation);

        log.info("Evaluation completed for application ID: {}. Approved: {}", applicationId, approved);
        return savedEvaluation;
    }
}
