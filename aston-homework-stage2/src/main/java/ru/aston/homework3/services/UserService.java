package ru.aston.homework3.services;

import ru.aston.homework2.entities.User;
import ru.aston.homework2.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User не может быть null");
        }
        userRepository.create(user);
    }

    public void update(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User или его ИД не могут быть null");
        }
        userRepository.update(user);
    }

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ИД не может быть null");
        }
        userRepository.delete(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User не может быть null");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User не найден с данным ИД: " + id));
    }
}
