package ru.aston.notification_service.service;

import org.springframework.stereotype.Service;
import ru.aston.kafka.dto.UserNotifyDTO;

@Service
public class UserNotificationService {
    private final EmailService emailService;

    public UserNotificationService(DefaultEmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyForCreation(Long id, UserNotifyDTO user) {
        if (!id.equals( user.id())) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        emailService.sendSimpleEmail(
                user.email(),
                String.format("%s, Вам рады!", user.name()),
                "Спасибо за регистрацию <3"
        );
    }

    public void notifyForDeletion(Long id, UserNotifyDTO user) {

        if (!id.equals( user.id())) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        emailService.sendSimpleEmail(
                user.email(),
                String.format("Увы %s, Вам более не рады!", user.name()),
                "Пока-пока!"
        );
    }
}
