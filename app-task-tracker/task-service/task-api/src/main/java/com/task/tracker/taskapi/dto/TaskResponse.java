package com.task.tracker.taskapi.dto;

import com.task.tracker.commonlib.dto.Priority;
import com.task.tracker.commonlib.dto.TaskStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Schema(description = "DTO ответа задачи")
public record TaskResponse(

        @Schema(description = "ID задачи")
        UUID id,

        @Schema(description = "Заголовок задачи", example = "Написать unit-тесты")
        String title,

        @Schema(
                description = "Описание задачи",
                example = "Покрыть сервисный слой тестами JUnit 5"
        )
        String description,

        @Schema(description = "Статус задачи", example = "IN_PROGRESS")
        TaskStatus status,

        @Schema(description = "Приоритет задачи", example = "HIGH")
        Priority priority,

        @Schema(description = "ID аккаунта (пользователя)")
        UUID accountId,

        @Schema(description = "Время создания задачи")
        Instant createdAt,

        @Schema(description = "Время завершения задачи")
        Instant completedAt,

        @Schema(description = "Время последнего обновления задачи")
        Instant updatedAt,

        @Schema(description = "Срок выполнения задачи")
        Instant dueDate,

        @ArraySchema(
                schema = @Schema(description = "ID связанных тегов")
        )
        Set<UUID> tagIds

) {}