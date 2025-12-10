package com.codeup.CoopCredit.domain.ports.in.member;

import com.codeup.CoopCredit.domain.model.Member;

public interface IGetMemberByDocumentUseCase {
    Member execute(String document);
}
