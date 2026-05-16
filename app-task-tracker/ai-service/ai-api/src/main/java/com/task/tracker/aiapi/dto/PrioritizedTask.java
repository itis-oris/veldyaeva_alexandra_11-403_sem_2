package com.task.tracker.aiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Результат AI приоритизации задачи")
public record PrioritizedTask(

        @Schema(description = "ID задачи", example = "b3b2f0e2-3c2c-4c2f-9d1f-1a2b3c4d5e6f")
        @NotNull(message = "taskId обязателен")
        UUID taskId,

        @Schema(description = "Рекомендуемый порядок выполнения (1 = самый важный)", example = "1")
        @Min(value = 1, message = "Порядок не может быть меньше 1")
        @Max(value = 1000, message = "Порядок слишком большой")
        int recommendedOrder,

        @Schema(description = "Рекомендованный приоритет (LOW, MEDIUM, HIGH)", example = "HIGH")
        @NotBlank(message = "Приоритет обязателен")
        String suggestedPriority,

        @Schema(description = "Причина решения AI", example = "Высокий бизнес-эффект и срочный дедлайн")
        @NotBlank(message = "Причина обязательна")
        String reason
) {
}