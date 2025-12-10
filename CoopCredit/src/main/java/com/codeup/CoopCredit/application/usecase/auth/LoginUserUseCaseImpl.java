package com.codeup.CoopCredit.application.usecase.auth;

import com.codeup.CoopCredit.domain.ports.in.auth.ILoginUserUseCase;
import com.codeup.CoopCredit.domain.ports.out.IUserRepositoryPort;
import com.codeup.CoopCredit.domain.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUserUseCaseImpl implements ILoginUserUseCase {

    private final IUserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String execute(String username, String password) {
        log.info("Attempting login for user: {}", username);

        IUserRepositoryPort.UserDetailsData user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.password())) {
            throw new AuthenticationException("Invalid username or password");
        }

        if (!user.enabled()) {
            throw new AuthenticationException("User account is disabled");
        }

        log.info("User authenticated successfully: {}", username);

        return username;
    }
}
