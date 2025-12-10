package com.codeup.CoopCredit.domain.ports.in.application;

import com.codeup.CoopCredit.domain.model.CreditApplication;

import java.util.List;

public interface IGetApplicationsByMemberUseCase {
    List<CreditApplication> execute(Long memberId);
}
