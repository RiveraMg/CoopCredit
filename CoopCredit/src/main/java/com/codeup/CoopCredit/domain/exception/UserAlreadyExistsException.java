package com.codeup.CoopCredit.domain.exception;

public class UserAlreadyExistsException extends DomainException {

    public UserAlreadyExistsException(String identifier, String type) {
        super(type + " already exists: " + identifier);
    }
}
