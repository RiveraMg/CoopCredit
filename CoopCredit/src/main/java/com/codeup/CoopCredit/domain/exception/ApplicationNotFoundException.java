package com.codeup.CoopCredit.domain.exception;

public class ApplicationNotFoundException extends DomainException {

    public ApplicationNotFoundException(Long applicationId) {
        super("Credit application not found with ID: " + applicationId);
    }
}