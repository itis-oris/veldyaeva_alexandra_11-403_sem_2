package com.task.tracker.authapi.dto;

import com.task.tracker.commonlib.dto.Role;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Ответ")
public record AccountResponse(

        @Schema(
                description = "Индентефикатор  аккаунта",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "Имя",
                example = "testuser123"
        )
        String username,

        @ArraySchema(
                schema = @Schema(
                        implementation = Role.class
                ),
                arraySchema = @Schema(
                        description = "Роли аккаунтов"
                )
        )
        Set<Role> roles

) {
}