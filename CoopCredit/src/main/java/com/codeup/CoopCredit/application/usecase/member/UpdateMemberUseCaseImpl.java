package com.codeup.CoopCredit.application.usecase.member;

import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.ports.in.member.IUpdateMemberUseCase;
import com.codeup.CoopCredit.domain.ports.out.IMemberRepositoryPort;
import com.codeup.CoopCredit.domain.exception.MemberNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateMemberUseCaseImpl implements IUpdateMemberUseCase {

    private final IMemberRepositoryPort memberRepository;

    @Override
    @Transactional
    public Member execute(Long memberId, Member updatedMember) {
        log.info("Updating member with ID: {}", memberId);

        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        // Update allowed fields
        if (updatedMember.getName() != null && !updatedMember.getName().trim().isEmpty()) {
            existingMember.setName(updatedMember.getName());
        }

        if (updatedMember.getSalary() != null && updatedMember.getSalary().compareTo(BigDecimal.ZERO) > 0) {
            existingMember.setSalary(updatedMember.getSalary());
        }

        if (updatedMember.getStatus() != null) {
            existingMember.setStatus(updatedMember.getStatus());
        }

        existingMember.setUpdatedAt(LocalDateTime.now());

        Member savedMember = memberRepository.save(existingMember);
        log.info("Member updated successfully with ID: {}", savedMember.getId());

        return savedMember;
    }
}
