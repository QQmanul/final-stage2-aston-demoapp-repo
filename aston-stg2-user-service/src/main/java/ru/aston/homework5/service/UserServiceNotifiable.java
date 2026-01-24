package ru.aston.homework5.service;

import org.springframework.stereotype.Service;
import ru.aston.homework4.dto.request.UserCreateDTO;
import ru.aston.homework4.dto.response.UserDTO;
import ru.aston.homework4.repository.UserRepositoryJpa;
import ru.aston.homework4.service.UserService;
import ru.aston.kafka.dto.UserNotifyDTO;
import ru.aston.homework5.kafka.UserKafkaProducer;

@Service
public class UserServiceNotifiable extends UserService {

    private final UserKafkaProducer kafkaProducer;

    public UserServiceNotifiable(UserRepositoryJpa userRepository, UserKafkaProducer kafkaProducer) {
        super(userRepository);
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public UserDTO create(UserCreateDTO createDTO) {
        var user =  super.create(createDTO);
        var userEvent = new UserNotifyDTO(
                user.id(),
                user.name(),
                user.email()
        );

        kafkaProducer.sendUserCreatedEvent(userEvent);
        return user;
    }

    @Override
    public void delete(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        super.delete(id);

        var userEvent = new UserNotifyDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        kafkaProducer.sendUserDeletedEvent(userEvent);
    }
}
