package com.codeup.CoopCredit.application.usecase.auth;

import com.codeup.CoopCredit.domain.ports.in.auth.IRegisterUserUseCase;
import com.codeup.CoopCredit.domain.ports.out.IUserRepositoryPort;
import com.codeup.CoopCredit.domain.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterUserUseCaseImpl implements IRegisterUserUseCase {

    private final IUserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long execute(String username, String password, String email, String roleName) {
        log.info("Registering new user: {}", username);

        // Validate uniqueness
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username, "Username");
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email, "Email");
        }

        // Validate role
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        // Encrypt password
        String encryptedPassword = passwordEncoder.encode(password);

        // Save user
        Long userId = userRepository.save(username, encryptedPassword, email, roleName);
        log.info("User registered successfully with ID: {}", userId);

        return userId;
    }
}
