package com.task.tracker.taskapi.dto;

import com.task.tracker.commonlib.dto.Priority;
import com.task.tracker.commonlib.dto.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Schema(description = "Запрос поиска и фильтрации задач")
public record TaskSearchRequest(

        @Schema(description = "Фильтр по статусу задачи")
        TaskStatus status,

        @Schema(description = "Фильтр по приоритету задачи")
        Priority priority,

        @NotNull(message = "ID пользователя не должен быть null")
        @Schema(
                description = "Фильтр по ID аккаунта (пользователя)",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID userId,

        @Schema(description = "Фильтр по точной дате выполнения")
        Instant dueDate,

        @Schema(
                description = "Поля для сортировки: status, priority, createdAt, dueDate, tagName",
                example = "[\"priority\", \"dueDate\"]"
        )
        List<String> sortBy,

        @Schema(
                description = "Фильтр по имени тега",
                example = "work"
        )
        String tagName

) {}