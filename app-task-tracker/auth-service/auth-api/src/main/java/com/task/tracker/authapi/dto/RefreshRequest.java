package com.task.tracker.authapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на обновление/отзыв токена")
public record RefreshRequest(
        @Schema(description = "Refresh token")
        @NotBlank String refreshToken
) {
}
