package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository;


import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.RiskEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRiskEvaluationJpaRepository extends JpaRepository<RiskEvaluationEntity, Long> {

    Optional<RiskEvaluationEntity> findByApplicationId(Long applicationId);
}
