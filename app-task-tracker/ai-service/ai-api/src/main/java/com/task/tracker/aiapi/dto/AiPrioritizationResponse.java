package com.task.tracker.aiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Schema(description = "Ответ AI на приоритизацию задач")
public record AiPrioritizationResponse(

        @Schema(description = "ID запроса для трассировки", example = "b3b2f0e2-3c2c-4c2f-9d1f-1a2b3c4d5e6f")
        @NotNull(message = "requestId обязателен")
        UUID requestId,

        @Schema(description = "Список рекомендаций по задачам")
        @NotNull(message = "recommendations не могут быть null")
        @Size(max = 50, message = "Максимум 50 рекомендаций")
        @Valid
        List<PrioritizedTask> recommendations,

        @Schema(description = "Общие рекомендации от AI", example = "Сначала закройте задачи с дедлайном до 3 дней")
        String generalAdvice,

        @Schema(description = "Количество входных токенов", example = "1200")
        int inputTokens,

        @Schema(description = "Количество выходных токенов", example = "450")
        int outputTokens
) {
}