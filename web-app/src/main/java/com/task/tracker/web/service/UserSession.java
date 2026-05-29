package com.task.tracker.web.service;

import lombok.Data;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.UUID;

@Data
public class UserSession implements Serializable {
    private String accessToken;
    private String refreshToken;
    private UUID accountId;
    private String username;
    private String role;

    private long validatedAt;

    public String bearer() {
        return accessToken;
    }

    public boolean loggedIn() {
        return accessToken != null && !accessToken.isBlank();
    }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

}
