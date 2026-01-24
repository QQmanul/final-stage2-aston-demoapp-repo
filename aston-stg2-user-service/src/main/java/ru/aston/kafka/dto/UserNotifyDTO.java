package ru.aston.kafka.dto;

public record UserNotifyDTO(
        Long id,
        String name,
        String email
) {}
