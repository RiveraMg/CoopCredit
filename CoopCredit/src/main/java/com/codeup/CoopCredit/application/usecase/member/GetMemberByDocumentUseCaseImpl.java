package com.codeup.CoopCredit.application.usecase.member;

import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.ports.in.member.IGetMemberByDocumentUseCase;
import com.codeup.CoopCredit.domain.ports.out.IMemberRepositoryPort;
import com.codeup.CoopCredit.domain.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMemberByDocumentUseCaseImpl implements IGetMemberByDocumentUseCase {

    private final IMemberRepositoryPort memberRepository;

    @Override
    public Member execute(String document) {
        log.info("Retrieving member with document: {}", document);

        return memberRepository.findByDocument(document)
                .orElseThrow(() -> new MemberNotFoundException(document));
    }
}
