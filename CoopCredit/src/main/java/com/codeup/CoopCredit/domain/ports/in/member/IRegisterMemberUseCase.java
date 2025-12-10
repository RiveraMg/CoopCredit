package com.codeup.CoopCredit.domain.ports.in.member;

import com.codeup.CoopCredit.domain.model.Member;

public interface IRegisterMemberUseCase {
    Member execute(Member member);
}
