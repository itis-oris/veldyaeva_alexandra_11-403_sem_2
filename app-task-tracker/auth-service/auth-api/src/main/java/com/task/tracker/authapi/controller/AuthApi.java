package com.task.tracker.authapi.controller;

import com.task.tracker.authapi.dto.AccountResponse;
import com.task.tracker.authapi.dto.ErrorResponse;

import com.task.tracker.authapi.dto.LoginResponse;
import com.task.tracker.authapi.dto.RefreshRequest;
import com.task.tracker.authapi.dto.SignUpRequest;
import com.task.tracker.authapi.dto.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "API аутентификации пользователей")
@RequestMapping("/auth")
public interface AuthApi {

    @Operation(
            summary = "Авторизация пользователя",
            description = "Возвращает пару access/refresh токенов. " +
                    "Вызывается только web-app (server-to-server), браузер токены не видит."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная авторизация",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestParam String username, @RequestParam String password);


    @Operation(
            summary = "Проверка access token (introspection)",
            description = "Единственная точка валидации JWT во всей системе. " +
                    "Возвращает подтверждённые id, username и роли владельца токена."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Токен валиден",
                    content = @Content(schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "401", description = "Токен отсутствует, истёк или невалиден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/validate")
    ResponseEntity<AccountResponse> validate(
            @Parameter(description = "JWT access token", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9...")
            @RequestHeader("Authorization") String authorizationHeader);


    @Operation(
            summary = "Обновление пары токенов",
            description = "Принимает refresh token, возвращает новую пару. " +
                    "Старый refresh token сразу отзывается (ротация)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Токены обновлены",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token недействителен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshRequest request);


    @Operation(
            summary = "Регистрация пользователя",
            description = "Создаёт новый аккаунт пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован",
                    content = @Content(schema = @Schema(implementation = SignUpResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Пользователь уже существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest request);


    @Operation(
            summary = "Logout",
            description = "Отзывает refresh token — сессия пользователя закрывается"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Выход выполнен")
    })
    @PostMapping("/logout")
    ResponseEntity<Void> logout(@Valid @RequestBody RefreshRequest request);
}
