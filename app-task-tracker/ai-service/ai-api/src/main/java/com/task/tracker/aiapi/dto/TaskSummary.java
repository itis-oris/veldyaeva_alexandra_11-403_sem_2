package com.task.tracker.aiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Краткое описание задачи для AI (приоритизация/анализ)")
public record TaskSummary(

        @Schema(description = "ID задачи", example = "b3b2f0e2-3c2c-4c2f-9d1f-1a2b3c4d5e6f")
        @NotNull(message = "ID задачи обязателен")
        UUID id,

        @Schema(description = "Название задачи", example = "Сделать авторизацию")
        @NotBlank(message = "Название задачи не может быть пустым")
        @Size(max = 100, message = "Максимум 100 символов")
        String title,

        @Schema(description = "Описание задачи", example = "Реализовать JWT login/register")
        @Size(max = 500, message = "Максимум 500 символов")
        String description,

        @Schema(description = "Приоритет (LOW, MEDIUM, HIGH)", example = "HIGH")
        @NotBlank(message = "Приоритет обязателен")
        String priority,

        @Schema(description = "Статус задачи", example = "IN_PROGRESS")
        @NotBlank(message = "Статус обязателен")
        String status,

        @Schema(description = "Дедлайн задачи", example = "2026-05-20T12:00:00Z")
        Instant dueDate
) {
}