package com.task.tracker.taskapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "DTO запроса для создания или обновления тега")
public record TagRequest(

        @Schema(description = "ID тега — null при создании, обязателен при обновлении")
        UUID id,

        @NotBlank(message = "Название тега не должно быть пустым")
        @Size(max = 100, message = "Название тега не должно превышать 100 символов")
        @Schema(description = "Название тега", example = "срочно")
        String name,

        @Size(max = 500, message = "Описание не должно превышать 500 символов")
        @Schema(description = "Описание тега", example = "Задачи высокого приоритета")
        String description,

        @Pattern(
                regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
                message = "Цвет должен быть в hex-формате, например #FF5733"
        )
        @Schema(description = "Цвет тега в hex-формате", example = "#FF5733")
        String color,

        @NotNull(message = "ID аккаунта не должен быть null")
        @Schema(description = "ID аккаунта (пользователя)")
        UUID accountId

) {}