package ru.aston.homework4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.homework2.entities.User;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {
}
