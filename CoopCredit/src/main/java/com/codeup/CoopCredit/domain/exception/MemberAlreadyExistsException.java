package com.codeup.CoopCredit.domain.exception;

public class MemberAlreadyExistsException extends DomainException {

    public MemberAlreadyExistsException(String document) {
        super("Member with document " + document + " already exists");
    }
}
