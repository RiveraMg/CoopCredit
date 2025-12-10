package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository;

import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
