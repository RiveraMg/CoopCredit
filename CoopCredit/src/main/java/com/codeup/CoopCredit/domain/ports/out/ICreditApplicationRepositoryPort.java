package com.codeup.CoopCredit.domain.ports.out;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.model.enums.ApplicationStatus;

import java.util.List;
import java.util.Optional;

public interface ICreditApplicationRepositoryPort {

    CreditApplication save(CreditApplication application);

    Optional<CreditApplication> findById(Long id);

    List<CreditApplication> findByMemberId(Long memberId);

    List<CreditApplication> findByStatus(ApplicationStatus status);

    void deleteById(Long id);
}
