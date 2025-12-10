package com.codeup.CoopCredit.domain.exception;

public class MemberNotActiveException extends DomainException {

    public MemberNotActiveException(Long memberId) {
        super("Member with ID " + memberId + " is not active. Only active members can request credit.");
    }
}
