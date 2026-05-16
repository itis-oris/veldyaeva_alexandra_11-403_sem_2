package com.task.tracker.authapi.dto;

import com.task.tracker.commonlib.dto.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Ответ после обновления пользователя")
public record UpdateResponse(

        @Schema(
                description = "ID пользователя",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "Имя пользователя (логин)",
                example = "updatedUser"
        )
        String username,

        @Schema(
                description = "Список ролей пользователя",
                example = "[\"USER\", \"ADMIN\"]"
        )
        Set<Role> roles

) {
}