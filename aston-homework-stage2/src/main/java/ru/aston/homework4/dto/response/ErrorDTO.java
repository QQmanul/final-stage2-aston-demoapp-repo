package ru.aston.homework4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Модель ошибки API")
public record ErrorDTO(
        @Schema(example = "400")
        int status,
        @Schema(example = "Ошибка валидации")
        String message,
        @Schema(example = "2025-01-11T15:30:00")
        LocalDateTime timestamp,
        @Schema(
                description = "Ошибки по полям",
                example = "{\"email\": \"Некорректный email\"}"
        )
        Map<String, String> errors
) {
    public ErrorDTO(int status, String message) {
        this(status, message, LocalDateTime.now(), null);
    }

    public ErrorDTO(int status, String message, Map<String, String> errors) {
        this(status, message, LocalDateTime.now(), errors);
    }
}