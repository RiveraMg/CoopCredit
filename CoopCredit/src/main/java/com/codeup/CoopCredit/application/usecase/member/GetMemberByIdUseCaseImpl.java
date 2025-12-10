package com.codeup.CoopCredit.application.usecase.member;

import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.ports.in.member.IGetMemberByIdUseCase;
import com.codeup.CoopCredit.domain.ports.out.IMemberRepositoryPort;
import com.codeup.CoopCredit.domain.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMemberByIdUseCaseImpl implements IGetMemberByIdUseCase {

    private final IMemberRepositoryPort memberRepository;

    @Override
    public Member execute(Long memberId) {
        log.info("Retrieving member with ID: {}", memberId);

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
