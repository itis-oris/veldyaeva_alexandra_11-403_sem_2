package com.task.tracker.commonlib.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ после обновления информации об аккаунте")
public record AccountUpdateResponse(

        @Schema(description = "Email пользователя", example = "user@example.com")
        String email,

        @Schema(description = "Количество опыта (XP) пользователя", example = "1200")
        Integer xpCount

) {
}
