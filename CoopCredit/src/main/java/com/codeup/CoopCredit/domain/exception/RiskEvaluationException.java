package com.codeup.CoopCredit.domain.exception;

public class RiskEvaluationException extends DomainException {

    public RiskEvaluationException(String message) {
        super("Risk evaluation error: " + message);
    }

    public RiskEvaluationException(String message, Throwable cause) {
        super("Risk evaluation error: " + message, cause);
    }
}