package com.codeup.CoopCredit.application.usecase.application;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.ports.in.application.IGetCreditApplicationByIdUseCase;
import com.codeup.CoopCredit.domain.ports.out.ICreditApplicationRepositoryPort;
import com.codeup.CoopCredit.domain.exception.ApplicationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetCreditApplicationByIdUseCaseImpl implements IGetCreditApplicationByIdUseCase {

    private final ICreditApplicationRepositoryPort applicationRepository;

    @Override
    public CreditApplication execute(Long applicationId) {
        log.info("Retrieving credit application with ID: {}", applicationId);

        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException(applicationId));
    }
}