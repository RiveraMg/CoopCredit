package com.codeup.CoopCredit.domain.ports.out;

import com.codeup.CoopCredit.domain.model.RiskEvaluation;

import java.util.Optional;

public interface IRiskEvaluationRepositoryPort {

    RiskEvaluation save(RiskEvaluation evaluation);

    Optional<RiskEvaluation> findById(Long id);

    Optional<RiskEvaluation> findByApplicationId(Long applicationId);

    void deleteById(Long id);
}
