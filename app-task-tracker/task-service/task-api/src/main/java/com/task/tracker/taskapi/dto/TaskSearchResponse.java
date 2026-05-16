package com.task.tracker.taskapi.dto;

import com.task.tracker.commonlib.dto.Priority;
import com.task.tracker.commonlib.dto.TaskStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Результат поиска задач")
public record TaskSearchResponse(

        @Schema(description = "ID задачи", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Заголовок задачи", example = "Написать unit-тесты")
        String title,

        @Schema(description = "Описание задачи", example = "Покрыть сервисный слой тестами")
        String description,

        @Schema(description = "Статус задачи", example = "IN_PROGRESS")
        TaskStatus status,

        @Schema(description = "Приоритет задачи", example = "HIGH")
        Priority priority,

        @Schema(description = "ID аккаунта (пользователя)", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID accountId,

        @Schema(description = "Дата создания задачи", example = "2026-01-01T10:00:00Z")
        Instant createdAt,

        @Schema(description = "Дедлайн задачи", example = "2026-12-31T23:59:00Z")
        Instant dueDate,

        @ArraySchema(
                schema = @Schema(description = "Теги, связанные с задачей")
        )
        Set<TagResponse> tags

) {}