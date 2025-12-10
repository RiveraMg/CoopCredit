package com.codeup.CoopCredit.domain.ports.out;

import java.math.BigDecimal;

public interface IRiskCentralPort {

    /**
     * Evaluate risk for a credit application
     * Calls the external risk-central-mock-service
     *
     * @param document Member document
     * @param amount Requested amount
     * @param termMonths Term in months
     * @return Risk evaluation response
     */
    RiskCentralResponse evaluate(String document, BigDecimal amount, Integer termMonths);

    /**
     * Response from Risk Central service
     */
    record RiskCentralResponse(
            String document,
            Integer score,
            String riskLevel,
            String details
    ) {}
}
