package ru.aston.homework5.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.aston.kafka.dto.UserNotifyDTO;

@Component
public class UserKafkaProducer  {

    private static final String TOPIC_USER_DELETED = "user-deleted";
    private static final String TOPIC_USER_CREATED = "user-created";

    private final KafkaTemplate<String, UserNotifyDTO> userKafkaTemplate;

    public UserKafkaProducer (KafkaTemplate<String, UserNotifyDTO> userKafkaTemplate) {
        this.userKafkaTemplate = userKafkaTemplate;
    }

    public void sendUserDeletedEvent(UserNotifyDTO dto) {
        userKafkaTemplate.send(TOPIC_USER_DELETED, dto.id().toString(), dto);
    }

    public void sendUserCreatedEvent(UserNotifyDTO dto) {
        userKafkaTemplate.send(TOPIC_USER_CREATED, dto.id().toString(), dto);
    }
}
