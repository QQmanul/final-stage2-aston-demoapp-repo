package ru.aston.homework4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.homework2.entities.User;
import ru.aston.homework4.dto.request.UserCreateDTO;
import ru.aston.homework4.dto.response.UserDTO;
import ru.aston.homework4.exeption.UserNotFoundException;
import ru.aston.homework4.repository.UserRepositoryJpa;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    protected final UserRepositoryJpa userRepository;

    public UserService(UserRepositoryJpa userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO create(UserCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Запрос не может быть null");
        }

        User user = new User(
                createDTO.name(),
                createDTO.email(),
                createDTO.age()
        );

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO update(Long id, UserCreateDTO createDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(createDTO.name());
        user.setEmail(createDTO.email());
        user.setAge(createDTO.age());

        return mapToResponse(userRepository.save(user));
    }

    private UserDTO mapToResponse(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt()
        );
    }
}