package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.adapter;


import com.codeup.CoopCredit.domain.ports.out.IUserRepositoryPort;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.RoleEntity;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.UserEntity;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper.UserEntityMapper;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository.IRoleJpaRepository;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository.IUserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryAdapter implements IUserRepositoryPort {

    private final IUserJpaRepository userRepository;
    private final IRoleJpaRepository roleRepository;
    private final UserEntityMapper mapper;

    @Override
    @Transactional
    public Long save(String username, String encryptedPassword, String email, String roleName) {
        log.debug("Saving user: {}", username);

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(encryptedPassword)
                .email(email)
                .enabled(true)
                .roles(Set.of(role))
                .build();

        UserEntity savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public Optional<UserDetailsData> findByUsername(String username) {
        log.debug("Finding user by username: {}", username);
        return userRepository.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
