package com.codeup.CoopCredit.domain.exception;

public class InvalidBusinessRuleException extends DomainException {

    public InvalidBusinessRuleException(String message) {
        super(message);
    }

    public InvalidBusinessRuleException(String field, String reason) {
        super("Invalid " + field + ": " + reason);
    }
}
