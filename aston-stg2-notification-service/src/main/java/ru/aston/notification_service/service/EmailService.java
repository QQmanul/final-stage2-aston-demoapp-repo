package ru.aston.notification_service.service;

public interface EmailService {
    void sendSimpleEmail(String toAddress, String subject, String message);
}
