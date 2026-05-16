package com.task.tracker.userimpl.exception;

import java.util.UUID;

public class AccountInfoNotFoundException extends RuntimeException {
    public AccountInfoNotFoundException(UUID message) {
        super("User not found: %s".formatted(message));
    }
}
