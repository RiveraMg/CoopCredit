package com.codeup.CoopCredit.domain.exception;

import com.codeup.CoopCredit.domain.model.enums.ApplicationStatus;

public class ApplicationNotPendingException extends DomainException {

    public ApplicationNotPendingException(Long applicationId, ApplicationStatus currentStatus) {
        super("Application with ID " + applicationId + " is not in PENDING status. Current status: " + currentStatus);
    }
}
