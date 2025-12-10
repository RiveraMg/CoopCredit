package com.codeup.CoopCredit.application.usecase.application;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.ports.in.application.IGetApplicationsByMemberUseCase;
import com.codeup.CoopCredit.domain.ports.out.ICreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetApplicationsByMemberUseCaseImpl implements IGetApplicationsByMemberUseCase {

    private final ICreditApplicationRepositoryPort creditApplicationRepository;

    @Override
    public List<CreditApplication> execute(Long memberId) {
        log.info("Getting applications for member: {}", memberId);
        return creditApplicationRepository.findByMemberId(memberId);
    }
}
