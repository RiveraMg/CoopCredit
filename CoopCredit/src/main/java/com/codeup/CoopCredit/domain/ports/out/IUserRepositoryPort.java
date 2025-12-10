package com.codeup.CoopCredit.domain.ports.out;

import java.util.Optional;

public interface IUserRepositoryPort {

     /*Save or update user*/
    Long save(String username, String encryptedPassword, String email, String roleName);

    /*Find user by username*/
    Optional<UserDetailsData> findByUsername(String username);

    /*Check if username exists*/
    boolean existsByUsername(String username);

    /*heck if email exists*/
    boolean existsByEmail(String email);

    /*Data transfer object for user details*/
    record UserDetailsData(
            Long id,
            String username,
            String password,
            String email,
            boolean enabled,
            String role
    ) {}
}