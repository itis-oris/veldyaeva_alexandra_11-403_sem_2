package com.task.tracker.notificationapi.controller;

import com.task.tracker.commonlib.dto.ErrorResponse;
import com.task.tracker.notificationapi.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Notifications",
        description = "API управления уведомлениями пользователя"
)
@RequestMapping("/api/notifications")
public interface NotificationApi {

    @Operation(
            summary = "Получить все уведомления пользователя",
            description = "Возвращает список уведомлений по ID аккаунта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение уведомлений",
                    content = @Content(schema = @Schema(implementation = NotificationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный UUID аккаунта",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{accountId}")
    ResponseEntity<List<NotificationResponse>> findAllByAccountId(

            @Parameter(
                    description = "ID аккаунта пользователя",
                    example = "b3b2f0e2-3c2c-4c2f-9d1f-1a2b3c4d5e6f",
                    required = true
            )
            @PathVariable UUID accountId
    );


    @Operation(
            summary = "Отметить уведомление как отправленное/прочитанное",
            description = "Обновляет статус уведомления"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Уведомление успешно обновлено"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Уведомление не найдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/send/{accountId}/{notificationId}")
    ResponseEntity<Void> markAsSend(

            @Parameter(
                    description = "ID аккаунта",
                    example = "b3b2f0e2-3c2c-4c2f-9d1f-1a2b3c4d5e6f",
                    required = true
            )
            @PathVariable UUID accountId,

            @Parameter(
                    description = "ID уведомления",
                    example = "c2c2f0e2-1a1a-4b4b-9d9d-123456789abc",
                    required = true
            )
            @PathVariable UUID notificationId
    );
}