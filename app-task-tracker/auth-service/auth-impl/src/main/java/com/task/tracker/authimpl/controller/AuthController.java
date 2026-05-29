package com.task.tracker.authimpl.controller;

import com.task.tracker.authapi.controller.AuthApi;
import com.task.tracker.authapi.dto.*;
import com.task.tracker.authimpl.jwt.service.JwtService;
import com.task.tracker.authimpl.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {
    private final AuthService authService;
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtService jwtService;

    @Override
    public ResponseEntity<LoginResponse> login(@RequestParam String username, @RequestParam String password) {
        log.info("login username: {}", username);
        TokenCouple tokenCouple = authService.login(username, password);
        return ResponseEntity.ok(new LoginResponse(tokenCouple.accessToken(), tokenCouple.refreshToken()));
    }

    @Override
    public ResponseEntity<AccountResponse> validate(@RequestHeader("Authorization") String authorizationHeader) {
        String token = stripBearer(authorizationHeader);
        return ResponseEntity.ok(jwtService.validateToken(token));
    }

    @Override
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        log.info("Refresh token update request");
        TokenCouple couple = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(new LoginResponse(couple.accessToken(), couple.refreshToken()));
    }

    @Override
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest registerRequest) {
        log.info("Account register request | username={}", registerRequest.username());
        SignUpResponse response = authService.signUp(registerRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> logout(
            @RequestBody RefreshRequest request) {
        log.info("Logout request");
        authService.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

    private String stripBearer(String header) {
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length()).trim();
        }
        return header == null ? "" : header.trim();
    }
}
