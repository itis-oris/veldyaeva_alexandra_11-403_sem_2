package com.task.tracker.taskapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Информация о теге")
public record TagResponse(

        UUID id,

        @Schema(description = "Название тега", example = "срочно")
        String name,

        @Schema(description = "Цвет тега в hex-формате", example = "#FF5733")
        String color,

        @Schema(description = "Описание тега")
        String description

) {}