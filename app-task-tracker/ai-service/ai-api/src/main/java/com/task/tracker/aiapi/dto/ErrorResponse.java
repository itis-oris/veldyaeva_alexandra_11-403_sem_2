package com.task.tracker.aiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Стандартный ответ об ошибке API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(

        @Schema(description = "HTTP статус", example = "400")
        int status,

        @Schema(description = "Тип ошибки", example = "BAD_REQUEST")
        String error,

        @Schema(description = "Сообщение ошибки", example = "Validation failed")
        String message,

        @Schema(description = "Путь запроса", example = "/ai/prioritize")
        String path,

        @Schema(description = "Время ошибки", example = "2026-05-16T10:15:30Z")
        Instant timestamp,

        @Schema(description = "Дополнительные детали ошибки ")
        Object details
) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(
                status,
                error,
                message,
                path,
                Instant.now(),
                null
        );
    }
}