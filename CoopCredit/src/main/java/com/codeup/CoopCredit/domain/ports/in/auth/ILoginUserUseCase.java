package com.codeup.CoopCredit.domain.ports.in.auth;

public interface ILoginUserUseCase {

    /**
     * Authenticate user and generate JWT token
     * @param username Username
     * @param password Raw password
     * @return JWT token
     */
    String execute(String username, String password);
}