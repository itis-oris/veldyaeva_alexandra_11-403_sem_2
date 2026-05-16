package com.task.tracker.taskapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Детали напоминания")
public record ReminderResponse(

        @Schema(description = "ID связанной задачи")
        UUID taskId,

        @Schema(description = "ID напоминания")
        UUID reminderId,

        @Schema(description = "Запланированное время напоминания")
        Instant reminderDate

) {}