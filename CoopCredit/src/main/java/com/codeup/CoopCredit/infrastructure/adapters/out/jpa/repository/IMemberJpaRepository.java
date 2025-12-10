package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository;

import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByDocument(String document);

    boolean existsByDocument(String document);
}
