package com.codeup.CoopCredit.domain.ports.in.application;

import com.codeup.CoopCredit.domain.model.CreditApplication;

public interface ICreateCreditApplicationUseCase {
    CreditApplication execute(CreditApplication application);
}
