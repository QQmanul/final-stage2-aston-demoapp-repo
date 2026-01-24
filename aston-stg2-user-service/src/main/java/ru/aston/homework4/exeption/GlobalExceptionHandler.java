package ru.aston.homework4.exeption;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aston.homework4.dto.response.ErrorDTO;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Ошибка валидации",
                errors
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleUserNotFound(UserNotFoundException ex) {
        return new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return new ErrorDTO(
                HttpStatus.CONFLICT.value(),
                "Пользователь с таким email уже существует"
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleIllegalArgument(IllegalArgumentException ex) {
        return new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllExceptions(Exception ex) {
        return new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Внутренняя ошибка сервера"
        );
    }
}
