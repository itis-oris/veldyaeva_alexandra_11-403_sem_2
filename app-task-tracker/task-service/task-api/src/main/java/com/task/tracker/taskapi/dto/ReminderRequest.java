package com.task.tracker.taskapi.dto;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO запроса для создания напоминания")
public record ReminderRequest(

        @NotNull(message = "ID задачи не должен быть null")
        @Schema(description = "ID задачи, для которой создаётся напоминание")
        UUID taskId,

        @NotNull(message = "Дата напоминания не должна быть null")
        @Future(message = "Дата напоминания должна быть в будущем")
        @Schema(
                description = "Время отправки напоминания (ISO-8601)",
                example = "2026-12-01T09:00:00Z"
        )
        Instant reminderDate

) {}