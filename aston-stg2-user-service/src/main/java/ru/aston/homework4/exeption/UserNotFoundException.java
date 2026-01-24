package ru.aston.homework4.exeption;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User не найден с данным ИД: " + id);
    }
}
