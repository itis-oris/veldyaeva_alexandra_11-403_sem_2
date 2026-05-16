package com.task.tracker.authapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ")
public record LoginResponse(

        @Schema(
                description = "JWT access token",
                example = "Bearer eyJhbGciOiJIUzUxMiJ9..."
        )
        String accessToken

) {
}