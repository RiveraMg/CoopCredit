package com.codeup.CoopCredit.domain.ports.in.auth;

public interface IRegisterUserUseCase {

    /**
     * Register a new user in the system
     * @param username Username
     * @param password Raw password (will be encrypted)
     * @param email Email
     * @param roleName Role name (ROLE_ADMIN, ROLE_ANALYST, ROLE_MEMBER)
     * @return User ID
     */
    Long execute(String username, String password, String email, String roleName);
}
