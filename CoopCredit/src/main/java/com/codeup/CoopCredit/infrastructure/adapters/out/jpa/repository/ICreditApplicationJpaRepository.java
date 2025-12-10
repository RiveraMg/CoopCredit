package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository;


import com.codeup.CoopCredit.domain.model.enums.ApplicationStatus;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.CreditApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICreditApplicationJpaRepository extends JpaRepository<CreditApplicationEntity, Long> {

    List<CreditApplicationEntity> findByMemberId(Long memberId);

    List<CreditApplicationEntity> findByStatus(ApplicationStatus status);
}
