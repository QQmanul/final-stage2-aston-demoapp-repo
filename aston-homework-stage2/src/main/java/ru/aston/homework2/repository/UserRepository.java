package ru.aston.homework2.repository;

import ru.aston.homework2.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void create(User user);
    void update(User user);
    void delete(Long id);
    List<User> findAll();
    Optional<User> findById(Long id);
}
