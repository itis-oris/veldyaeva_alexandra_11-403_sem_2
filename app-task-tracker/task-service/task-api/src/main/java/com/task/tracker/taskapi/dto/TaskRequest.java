package com.task.tracker.taskapi.dto;

import com.task.tracker.commonlib.dto.Priority;
import com.task.tracker.commonlib.dto.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Schema(description = "DTO запроса для создания или обновления задачи")
public record TaskRequest(

        @Schema(description = "ID задачи — null при создании, обязателен при обновлении")
        UUID id,

        @NotBlank(message = "Заголовок не должен быть пустым")
        @Size(max = 255, message = "Заголовок не должен превышать 255 символов")
        @Schema(description = "Заголовок задачи", example = "Написать unit-тесты")
        String title,

        @Size(max = 2000, message = "Описание не должно превышать 2000 символов")
        @Schema(description = "Описание задачи", example = "Покрыть сервисный слой тестами JUnit 5")
        String description,

        @Schema(description = "Статус задачи", example = "IN_PROGRESS")
        TaskStatus status,

        @NotNull(message = "Приоритет не должен быть null")
        @Schema(description = "Приоритет задачи", example = "HIGH")
        Priority priority,

        @NotNull(message = "ID аккаунта не должен быть null")
        @Schema(description = "ID аккаунта (пользователя)")
        UUID accountId,

        @Schema(description = "Дата выполнения задачи", example = "2026-12-31T23:59:00Z")
        Instant dueDate,

        @Schema(description = "Набор ID тегов, связанных с задачей")
        Set<UUID> tagIds

) {}