package com.task.tracker.authapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Пара токенов доступа и обновления")
public record TokenCouple(

        @Schema(
                description = "JWT access token",
                example = "eyJhbGciOiJIUzUxMiJ9..."
        )
        @NotBlank(message = "Access token не должен быть пустым")
        String accessToken,

        @Schema(
                description = "JWT refresh token",
                example = "eyJhbGciOiJIUzUxMiJ9..."
        )
        @NotBlank(message = "Refresh token не должен быть пустым")
        String refreshToken,

        @Schema(
                description = "Время жизни refresh token в миллисекундах",
                example = "604800000"
        )
        @NotNull(message = "Время жизни refresh token обязательно")
        Long refreshTokenExpiration
) {
}