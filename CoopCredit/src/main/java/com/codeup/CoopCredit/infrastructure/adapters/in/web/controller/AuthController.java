package com.codeup.CoopCredit.infrastructure.adapters.in.web.controller;

import com.codeup.CoopCredit.domain.ports.in.auth.ILoginUserUseCase;
import com.codeup.CoopCredit.domain.ports.in.auth.IRegisterUserUseCase;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.LoginDTO;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.RegisterUserDTO;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.response.AppResponse;
import com.codeup.CoopCredit.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IRegisterUserUseCase registerUserUseCase;
    private final ILoginUserUseCase loginUserUseCase;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AppResponse<Map<String, Object>>> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        log.info("Registering new user: {}", registerUserDTO.getUsername());
        
        try {
            Long userId = registerUserUseCase.execute(
                    registerUserDTO.getUsername(),
                    registerUserDTO.getPassword(),
                    registerUserDTO.getRole(),
                    registerUserDTO.getEmail()
            );
            
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("id", userId);
            data.put("username", registerUserDTO.getUsername());
            data.put("role", registerUserDTO.getRole());
            data.put("message", "User registered successfully");
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AppResponse.ok(data));
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse<Map<String, Object>>> login(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("Login attempt for user: {}", loginDTO.getUsername());
        
        try {
            String username = loginUserUseCase.execute(loginDTO.getUsername(), loginDTO.getPassword());
            String token = jwtTokenProvider.generateToken(username);
            
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("token", token);
            data.put("username", username);
            data.put("type", "Bearer");
            
            return ResponseEntity.ok(AppResponse.ok(data));
        } catch (Exception e) {
            log.error("Login failed for user: {}", loginDTO.getUsername());
            throw e;
        }
    }
}
