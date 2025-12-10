package com.codeup.CoopCredit.domain.ports.in.application;

import com.codeup.CoopCredit.domain.model.RiskEvaluation;

public interface IEvaluateCreditApplicationUseCase {
    RiskEvaluation execute(Long applicationId);
}
