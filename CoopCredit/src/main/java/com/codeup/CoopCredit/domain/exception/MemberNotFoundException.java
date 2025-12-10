package com.codeup.CoopCredit.domain.exception;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException(Long memberId) {
        super("Member not found with ID: " + memberId);
    }

    public MemberNotFoundException(String document) {
        super("Member not found with document: " + document);
    }
}
