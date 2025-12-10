package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.adapter;


import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.ports.out.IMemberRepositoryPort;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.MemberEntity;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper.MemberEntityMapper;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository.IMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryAdapter implements IMemberRepositoryPort {

    private final IMemberJpaRepository repository;
    private final MemberEntityMapper mapper;

    @Override
    public Member save(Member member) {
        log.debug("Saving member: {}", member.getDocument());
        MemberEntity entity = mapper.toEntity(member);
        MemberEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Member> findById(Long id) {
        log.debug("Finding member by id: {}", id);
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Member> findByDocument(String document) {
        log.debug("Finding member by document: {}", document);
        return repository.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByDocument(String document) {
        return repository.existsByDocument(document);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting member by id: {}", id);
        repository.deleteById(id);
    }
}
