package com.task.tracker.authapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с парой токенов")
public record LoginResponse(

        @Schema(description = "JWT access token", example = "Bearer eyJhbGciOiJIUzUxMiJ9...")
        String accessToken,

        @Schema(description = "JWT refresh token")
        String refreshToken
) {
}
