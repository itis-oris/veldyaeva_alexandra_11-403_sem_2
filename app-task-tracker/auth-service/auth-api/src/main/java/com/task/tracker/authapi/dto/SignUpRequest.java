package com.task.tracker.authapi.dto;

import com.task.tracker.commonlib.dto.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.Instant;

@Schema(description = "Запрос на регистрацию пользователя")
public record SignUpRequest(

        @Schema(
                description = "Имя пользователя (логин)",
                example = "testuser123"
        )
        @NotBlank(message = "Имя пользователя не должно быть пустым")
        String username,

        @Schema(
                description = "Пароль пользователя",
                example = "StrongPassword123!"
        )
        @NotBlank(message = "Пароль не должен быть пустым")
        String rawPassword,

        @Schema(
                description = "Роль пользователя",
                example = "USER"
        )
        @NotNull(message = "Роль обязательна")
        Role role,

        @Schema(
                description = "Дата рождения пользователя (должна быть в прошлом)",
                example = "2000-05-10T00:00:00Z"
        )
        @Past(message = "Дата рождения должна быть в прошлом")
        Instant birthday,

        @Schema(
                description = "Email пользователя",
                example = "user@example.com"
        )
        @NotBlank(message = "Email не должен быть пустым")
        @Email(message = "Некорректный email")
        String email
) {
}