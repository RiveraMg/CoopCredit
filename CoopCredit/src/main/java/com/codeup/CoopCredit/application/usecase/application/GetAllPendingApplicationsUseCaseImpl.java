package com.codeup.CoopCredit.application.usecase.application;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.model.enums.ApplicationStatus;
import com.codeup.CoopCredit.domain.ports.in.application.IGetAllPendingApplicationsUseCase;
import com.codeup.CoopCredit.domain.ports.out.ICreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllPendingApplicationsUseCaseImpl implements IGetAllPendingApplicationsUseCase {

    private final ICreditApplicationRepositoryPort creditApplicationRepository;

    @Override
    public List<CreditApplication> execute() {
        log.info("Getting all pending applications");
        return creditApplicationRepository.findByStatus(ApplicationStatus.PENDING);
    }
}
