package com.task.tracker.aiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ от Groq")
public record AiResponse(

        @Schema(description = "Сгенерированный текст модели")
        String text,

        @Schema(description = "Количество входных токенов")
        int inputTokens,

        @Schema(description = "Количество выходных токенов")
        int outputTokens
) {
}