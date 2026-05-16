package com.task.tracker.notificationapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Уведомление пользователя")
public record NotificationResponse(

        @Schema(
                description = "ID уведомления",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "Заголовок уведомления",
                example = "Новая задача"
        )
        String title,

        @Schema(
                description = "Тип уведомления",
                example = "TASK_CREATED"
        )
        String type,

        @Schema(
                description = "Текст уведомления",
                example = "Вам назначена новая задача"
        )
        String body
) {
}