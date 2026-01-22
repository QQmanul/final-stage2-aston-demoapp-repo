package ru.aston.homework4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Модель пользователя, возвращаемая API")
public record UserDTO(
        @Schema(
                description = "Идентификатор пользователя",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Имя пользователя",
                example = "Иван"
        )
        String name,

        @Schema(
                description = "Email пользователя",
                example = "ivan@site.ru"
        )
        String email,

        @Schema(
                description = "Возраст пользователя",
                example = "33"
        )
        Integer age,

        @Schema(
                description = "Дата и время создания пользователя",
                example = "2025-01-11T14:30:00"
        )
        LocalDateTime createdAt
) {}
