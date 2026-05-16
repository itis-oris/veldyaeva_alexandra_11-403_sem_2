package com.task.tracker.taskapi.dto;

import com.task.tracker.commonlib.dto.Priority;
import com.task.tracker.commonlib.dto.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Сокращённое представление задачи для недельного отображения (без тегов)")
public record TaskWeeklyResponse(

        @Schema(description = "Уникальный идентификатор задачи")
        UUID id,

        @Schema(description = "Название задачи", example = "Написать модульные тесты")
        String title,

        @Schema(description = "Описание задачи", example = "Покрыть сервисный слой тестами JUnit 5")
        String description,

        @Schema(description = "Текущий статус задачи", example = "IN_PROGRESS")
        TaskStatus status,

        @Schema(description = "Приоритет задачи", example = "HIGH")
        Priority priority,

        @Schema(description = "ID аккаунта")
        UUID accountId,

        @Schema(description = "Время создания задачи", example = "2026-05-16T10:15:30Z")
        Instant createdAt,

        @Schema(description = "Срок выполнения задачи", example = "2026-06-01T23:59:00Z")
        Instant dueDate

) {
}