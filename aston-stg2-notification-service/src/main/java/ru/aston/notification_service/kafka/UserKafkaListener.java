package ru.aston.notification_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.aston.kafka.dto.UserNotifyDTO;
import ru.aston.notification_service.service.UserNotificationService;

@Component
public class UserKafkaListener {

    private final UserNotificationService notificationService;

    public UserKafkaListener(UserNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "user-created")
    public void createUserListener(UserNotifyDTO user) {
        notificationService.notifyForCreation(user.id(), user);
    }

    @KafkaListener(topics = "user-deleted")
    public void handleUserDeleted(UserNotifyDTO user) {
        notificationService.notifyForDeletion(user.id(), user);
    }
}
