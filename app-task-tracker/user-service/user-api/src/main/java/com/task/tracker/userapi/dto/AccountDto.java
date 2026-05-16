package com.task.tracker.userapi.dto;

import com.task.tracker.commonlib.dto.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Информация об аккаунте пользователя")
public record AccountDto(

        @Schema(description = "Уникальный идентификатор аккаунта")
        UUID id,

        @Schema(description = "Email пользователя", example = "user@example.com")
        String email,

        @Schema(description = "Имя пользователя", example = "john_doe")
        String username,

        @Schema(description = "Количество опыта (XP)", example = "1500")
        Integer xp,

        @Schema(description = "Статус аккаунта", example = "ACTIVE")
        AccountStatus accountStatus

) {
}