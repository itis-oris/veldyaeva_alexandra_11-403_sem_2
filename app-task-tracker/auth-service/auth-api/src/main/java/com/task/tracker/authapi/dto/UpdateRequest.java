package com.task.tracker.authapi.dto;

import com.task.tracker.commonlib.dto.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Запрос на обновление пользователя")
public record UpdateRequest(

        @Schema(
                description = "ID пользователя",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        @NotNull(message = "ID пользователя обязателен")
        UUID id,

        @Schema(
                description = "Новое имя пользователя (логин)",
                example = "newusername"
        )
        String username,

        @Schema(
                description = "Новая роль пользователя",
                example = "USER"
        )
        Role role

) {
}