package com.codeup.CoopCredit.domain.ports.in.member;

import com.codeup.CoopCredit.domain.model.Member;

public interface IGetMemberByIdUseCase {
    Member execute(Long memberId);
}