package com.codeup.CoopCredit.application.usecase.member;

import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.model.enums.MemberStatus;
import com.codeup.CoopCredit.domain.ports.in.member.IRegisterMemberUseCase;
import com.codeup.CoopCredit.domain.ports.out.IMemberRepositoryPort;
import com.codeup.CoopCredit.domain.exception.InvalidBusinessRuleException;
import com.codeup.CoopCredit.domain.exception.MemberAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterMemberUseCaseImpl implements IRegisterMemberUseCase {

    private final IMemberRepositoryPort memberRepository;

    @Override
    @Transactional
    public Member execute(Member member) {
        log.info("Registering new member with document: {}", member.getDocument());

        // Validate document uniqueness
        if (memberRepository.existsByDocument(member.getDocument())) {
            throw new MemberAlreadyExistsException(member.getDocument());
        }

        // Business validations
        validateMember(member);

        // Set defaults
        if (member.getStatus() == null) {
            member.setStatus(MemberStatus.ACTIVE);
        }
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());

        Member savedMember = memberRepository.save(member);
        log.info("Member registered successfully with ID: {}", savedMember.getId());

        return savedMember;
    }

    private void validateMember(Member member) {
        if (member.getDocument() == null || member.getDocument().trim().isEmpty()) {
            throw new InvalidBusinessRuleException("document", "Document is required");
        }

        if (member.getName() == null || member.getName().trim().isEmpty()) {
            throw new InvalidBusinessRuleException("name", "Name is required");
        }

        if (member.getSalary() == null || member.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBusinessRuleException("salary", "Salary must be greater than zero");
        }

        if (member.getMembershipDate() == null) {
            throw new InvalidBusinessRuleException("membershipDate", "Membership date is required");
        }
    }
}
