package ru.aston.homework4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Модель данных для создания пользователя")
public record UserCreateDTO(
        @NotBlank(message = "Имя обязательно")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        @Schema(description = "Имя пользователя",
                example = "Иван",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @NotBlank(message = "Email обязателен")
        @Email(message = "Некорректный формат email")
        @Schema(description = "Уникальная почта пользователя",
                example = "ivan@site.ru",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @Min(value = 0, message = "Возраст не может быть отрицательным")
        @Max(value = 150, message = "Введите корректный возраст")
        @Schema(description = "Возраст пользователя",
                example = "33",
                requiredMode = Schema.RequiredMode.REQUIRED)
        Integer age
) {}