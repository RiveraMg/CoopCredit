package com.codeup.CoopCredit.domain.ports.out;

import com.codeup.CoopCredit.domain.model.Member;

import java.util.Optional;

public interface IMemberRepositoryPort {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByDocument(String document);

    boolean existsByDocument(String document);

    void deleteById(Long id);
}
