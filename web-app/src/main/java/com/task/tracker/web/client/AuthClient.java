package com.task.tracker.web.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.task.tracker.web.config.ServiceProperties;
import com.task.tracker.web.dto.Identity;
import com.task.tracker.web.dto.LoginForm;
import com.task.tracker.web.dto.RegisterForm;
import com.task.tracker.web.dto.TokenPair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthClient {

    private final RestTemplate restTemplate;
    private final ServiceProperties serviceProperties;

    public TokenPair login(LoginForm loginForm) {
        Map<String, Object> body = Map.of(
                "username", loginForm.getUsername(),
                "password", loginForm.getPassword()
        );
        try {
            JsonNode response = restTemplate.postForEntity(
                    serviceProperties.getAuthUrl() + "/auth/login",
                    jsonEntity(body), JsonNode.class).getBody();
            return toTokenPair(response);
        } catch (HttpClientErrorException e) {
            log.warn("Login failed {}", e.getStatusCode());
            throw new RuntimeException("Неверное имя пользователя или пароль");
        }
    }

    public Identity validate(String bearer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, bearer);
        try {
            JsonNode body = restTemplate.postForEntity(
                    serviceProperties.getAuthUrl() + "/auth/validate",
                    new HttpEntity<>(headers), JsonNode.class).getBody();

            if (body == null) {
                return null;
            }
            String role = "USER";
            JsonNode roles = body.path("roles");
            if (roles.isArray() && roles.size() > 0) {
                role = roles.get(0).asText("USER").replace("ROLE_", "");
                for (JsonNode r : roles) {
                    if ("ADMIN".equals(r.asText())) {
                        role = "ADMIN";
                    }
                }
            }
            return new Identity(
                    java.util.UUID.fromString(body.path("id").asText()),
                    body.path("username").asText("?"),
                    role
            );
        } catch (HttpClientErrorException.Unauthorized e) {
            return null;
        } catch (Exception e) {
            log.error("Auth validate error: {}", e.getMessage());
            return null;
        }
    }

    public void register(RegisterForm registerForm) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", registerForm.getUsername());
        body.put("rawPassword", registerForm.getRawPassword());
        body.put("email", registerForm.getEmail() != null ? registerForm.getEmail() : "");
        body.put("role", "USER");
        if (registerForm.getBirthday() != null && !registerForm.getBirthday().isBlank()) {
            body.put("birthday", registerForm.getBirthday() + "T00:00:00Z");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            restTemplate.postForEntity(serviceProperties.getAuthUrl() + "/auth/register",
                    new HttpEntity<>(body, httpHeaders), JsonNode.class);
        } catch (HttpClientErrorException e) {
            log.warn("Register failed {}", e.getStatusCode());
            throw new RuntimeException("пользователь уже существует");
        }
    }

    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return;
        }
        try {
            restTemplate.postForEntity(
                    serviceProperties.getAuthUrl() + "/auth/logout",
                    jsonEntity(Map.of("refreshToken", refreshToken)),
                    Void.class);
            log.info("Refresh token revoked");
        } catch (Exception e) {
            log.warn("Could not call auth logout: {}", e.getMessage());
        }
    }

    public TokenPair refresh(String refreshToken) {
        try {
            JsonNode response = restTemplate.postForEntity(
                    serviceProperties.getAuthUrl() + "/auth/refresh",
                    jsonEntity(Map.of("refreshToken", refreshToken)),
                    JsonNode.class).getBody();
            return toTokenPair(response);
        } catch (Exception e) {
            log.warn("Refresh failed: {}", e.getMessage());
            return null;
        }
    }

    private TokenPair toTokenPair(JsonNode response) {
        String access = response != null ? response.path("accessToken").asText(null) : null;
        String refresh = response != null ? response.path("refreshToken").asText(null) : null;
        if (access == null || access.isBlank()) {
            throw new RuntimeException("Сервер нее  вернул токен");
        }
        return new TokenPair(access, refresh);
    }

    private HttpEntity<Map<String, Object>> jsonEntity(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }
}
