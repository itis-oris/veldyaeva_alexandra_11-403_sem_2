package com.task.tracker.authimpl.exception;

import com.task.tracker.authapi.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExists(
            UsernameAlreadyExistsException ex, HttpServletRequest req) {
        log.warn("Username already exists | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error(409, "USERNAME_ALREADY_EXISTS", ex.getMessage(), req));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            AccountNotFoundException ex, HttpServletRequest req) {
        log.warn("Account not found | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error(404, "ACCOUNT_NOT_FOUND", ex.getMessage(), req));
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleNotActive(
            AccountNotActiveException ex, HttpServletRequest req) {
        log.warn("Account not active | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error(403, "ACCOUNT_NOT_ACTIVE", ex.getMessage(), req));
    }

    @ExceptionHandler(JwtNotValidException.class)
    public ResponseEntity<ErrorResponse> handleJwtInvalid(
            JwtNotValidException ex, HttpServletRequest req) {
        log.warn("JWT not valid | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error(401, "JWT_NOT_VALID", ex.getMessage(), req));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(
            InvalidTokenException ex, HttpServletRequest req) {
        log.warn("Invalid token | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error(401, "INVALID_TOKEN", ex.getMessage(), req));
    }

    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSession(
            InvalidSessionException ex, HttpServletRequest req) {
        log.warn("Invalid session | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error(401, "INVALID_SESSION", ex.getMessage(), req));
    }

    @ExceptionHandler(AuthenticationHeaderException.class)
    public ResponseEntity<ErrorResponse> handleAuthHeader(
            AuthenticationHeaderException ex, HttpServletRequest req) {
        log.warn("Auth header error | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error(401, "AUTH_HEADER_ERROR", ex.getMessage(), req));
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ErrorResponse> handleTooMany(
            TooManyRequestsException ex, HttpServletRequest req) {
        log.warn("Too many requests | path={} | ip={}", req.getRequestURI(), req.getRemoteAddr());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(error(429, "TOO_MANY_REQUESTS", ex.getMessage(), req));
    }

    @ExceptionHandler(NoSuchDefaultAccountRoleException.class)
    public ResponseEntity<ErrorResponse> handleNoRole(
            NoSuchDefaultAccountRoleException ex, HttpServletRequest req) {
        log.error("No default role configured | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(500, "ROLE_CONFIG_ERROR", ex.getMessage(), req));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        log.warn("Validation error | path={} | errors={}", req.getRequestURI(), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(
                        400,
                        "VALIDATION_ERROR",
                        errors.toString(),
                        req.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception ex, HttpServletRequest req) {
        log.error("Unexpected error | path={} | msg={}", req.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(500, "INTERNAL_ERROR", "Внутренняя ошибка сервера", req));
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            Exception ex, HttpServletRequest req) {
        log.warn("Bad credentials | path={}", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(401, "BAD_CREDENTIALS",
                        "Неверное имя пользователя или пароль",
                        req.getRequestURI()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(
            Exception ex, HttpServletRequest req) {
        log.warn("Authentication failed | path={} | msg={}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(401, "AUTHENTICATION_FAILED",
                        ex.getMessage(),
                        req.getRequestURI()));
    }

    private ErrorResponse error(int status, String code, String message, HttpServletRequest req) {
        return ErrorResponse.of(status, code, message, req.getRequestURI());
    }
}
