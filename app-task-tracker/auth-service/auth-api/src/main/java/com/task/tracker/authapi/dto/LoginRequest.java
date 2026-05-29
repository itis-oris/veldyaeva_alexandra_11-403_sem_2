package com.task.tracker.authapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на вход")
public record LoginRequest(
        @Schema(description = "Имя пользователя", example = "admin")
        @NotBlank String username,

        @Schema(description = "Пароль", example = "password123")
        @NotBlank String password
) {
}
