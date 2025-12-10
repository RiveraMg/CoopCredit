package com.codeup.CoopCredit.application.usecase.application;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.model.enums.ApplicationStatus;
import com.codeup.CoopCredit.domain.ports.in.application.ICreateCreditApplicationUseCase;
import com.codeup.CoopCredit.domain.ports.out.ICreditApplicationRepositoryPort;
import com.codeup.CoopCredit.domain.ports.out.IMemberRepositoryPort;
import com.codeup.CoopCredit.domain.exception.InvalidBusinessRuleException;
import com.codeup.CoopCredit.domain.exception.MemberNotActiveException;
import com.codeup.CoopCredit.domain.exception.MemberNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCreditApplicationUseCaseImpl implements ICreateCreditApplicationUseCase {

    private final ICreditApplicationRepositoryPort applicationRepository;
    private final IMemberRepositoryPort memberRepository;

    @Override
    @Transactional
    public CreditApplication execute(CreditApplication application) {
        log.info("Creating credit application for member ID: {}", application.getMemberId());

        // Validate member exists and is active
        Member member = memberRepository.findById(application.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(application.getMemberId()));

        if (!member.isActive()) {
            throw new MemberNotActiveException(member.getId());
        }

        // Business validations
        validateApplication(application, member);

        // Set defaults
        application.setStatus(ApplicationStatus.PENDING);
        application.setApplicationDate(LocalDateTime.now());
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());

        CreditApplication savedApplication = applicationRepository.save(application);
        log.info("Credit application created successfully with ID: {}", savedApplication.getId());

        return savedApplication;
    }

    private void validateApplication(CreditApplication application, Member member) {
        if (application.getRequestedAmount() == null || application.getRequestedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBusinessRuleException("requestedAmount", "Requested amount must be greater than zero");
        }

        if (application.getTermMonths() == null || application.getTermMonths() < 6 || application.getTermMonths() > 120) {
            throw new InvalidBusinessRuleException("termMonths", "Term must be between 6 and 120 months");
        }

        if (application.getProposedRate() == null || application.getProposedRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidBusinessRuleException("proposedRate", "Proposed rate must be non-negative");
        }

        // Validate member meets minimum seniority
        if (!member.meetsMinimumSeniority()) {
            throw new InvalidBusinessRuleException("seniority", "Member does not meet minimum seniority requirement (6 months)");
        }

        // Validate requested amount doesn't exceed maximum
        if (!member.canRequestAmount(application.getRequestedAmount())) {
            throw new InvalidBusinessRuleException("requestedAmount",
                    "Requested amount exceeds maximum allowed (4x salary: %s)".formatted(member.getMaximumLoanAmount()));
        }
    }
}
