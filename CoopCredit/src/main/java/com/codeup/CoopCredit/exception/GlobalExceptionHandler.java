package com.codeup.CoopCredit.exception;

import com.codeup.CoopCredit.domain.exception.*;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.response.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<AppResponse<Object>> handleMemberNotFoundException(
            MemberNotFoundException ex, WebRequest request) {
        log.warn("Member not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(MemberAlreadyExistsException.class)
    public ResponseEntity<AppResponse<Object>> handleMemberAlreadyExistsException(
            MemberAlreadyExistsException ex, WebRequest request) {
        log.warn("Member already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(MemberNotActiveException.class)
    public ResponseEntity<AppResponse<Object>> handleMemberNotActiveException(
            MemberNotActiveException ex, WebRequest request) {
        log.warn("Member not active: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<AppResponse<Object>> handleApplicationNotFoundException(
            ApplicationNotFoundException ex, WebRequest request) {
        log.warn("Application not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ApplicationNotPendingException.class)
    public ResponseEntity<AppResponse<Object>> handleApplicationNotPendingException(
            ApplicationNotPendingException ex, WebRequest request) {
        log.warn("Application not pending: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvalidBusinessRuleException.class)
    public ResponseEntity<AppResponse<Object>> handleInvalidBusinessRuleException(
            InvalidBusinessRuleException ex, WebRequest request) {
        log.warn("Invalid business rule: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(RiskEvaluationException.class)
    public ResponseEntity<AppResponse<Object>> handleRiskEvaluationException(
            RiskEvaluationException ex, WebRequest request) {
        log.error("Risk evaluation error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AppResponse<Object>> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {
        log.warn("User already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AppResponse<Object>> handleAuthenticationException(
            AuthenticationException ex, WebRequest request) {
        log.warn("Authentication failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AppResponse<Object>> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(createErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AppResponse<Object>> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(createErrorResponse("Access denied", HttpStatus.FORBIDDEN));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.warn("Validation error: {}", ex.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(AppResponse.error("Validation failed", generateTraceId()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<AppResponse<Object>> handleDomainException(
            DomainException ex, WebRequest request) {
        log.warn("Domain exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse<Object>> handleGlobalException(
            Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private AppResponse<Object> createErrorResponse(String message, HttpStatus status) {
        String traceId = generateTraceId();
        return new AppResponse<>(
                "error",
                null,
                new AppResponse.Meta(message, traceId, "1.0.0", null)
        );
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}
