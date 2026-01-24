package ru.aston.homework4.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.homework2.entities.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryJpaTest {
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.database-platform",
                () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired
    private UserRepositoryJpa userRepository;
    @Test
    void when_createUser_then_idIsGenerated() {
        User vova = new User("Vova", "Vova@gov.de", 21);

        userRepository.save(vova);

        assertNotNull(vova.getId(), "ID не присвоен");
    }

    @Test
    void when_createValidUser_then_userIsSavedToDatabase() {
        User vova = new User("Vova", "Vova@gov.de", 21);

        userRepository.save(vova);

        Optional<User> savedUser = userRepository.findById(vova.getId());
        assertTrue(savedUser.isPresent(), "Пользователь не найден");
        assertEquals("Vova", savedUser.get().getName());
        assertEquals("Vova@gov.de", savedUser.get().getEmail());
    }

    @Test
    void when_findById_withNonExistentId_then_returnsEmptyOptional() {
        Long nonExistentId = 9999L;

        Optional<User> result = userRepository.findById(nonExistentId);

        assertTrue(result.isEmpty(), "Должен вернуться пустой Optional");
    }

    @Test
    void when_deleteUser_then_itCannotBeFoundAnymore() {
        User nikita = new User("Nikita", "Nikita@gov.de", 21);
        nikita = userRepository.save(nikita);

        Long id = nikita.getId();
        assertTrue(userRepository.findById(id).isPresent(), "Пользователь не создался");

        userRepository.deleteById(id);

        Optional<User> deletedUser = userRepository.findById(id);
        assertTrue(deletedUser.isEmpty(), "Пользователь не удалился");
    }

    @Test
    void when_updateUserAge_then_changesArePersisted() {
        User sveta = new User("Sveta", "Sveta@gov.de", 21);
        sveta = userRepository.save(sveta);
        Long id = sveta.getId();

        int newAge = 19;
        sveta.setAge(newAge);

        userRepository.save(sveta);

        User updatedUser = userRepository.findById(id).orElseThrow();
        assertEquals(newAge, updatedUser.getAge(), "Возраст не соответствует значению");
    }

    @Test
    void when_findAll_then_returnAllSavedUsers() {
        User sveta = new User("Sveta", "Sveta@gov.de", 21);
        User nikita = new User("Nikita", "Nikita@gov.de", 21);

        userRepository.save(sveta);
        userRepository.save(nikita);

        List<User> users = userRepository.findAll();

        assertNotNull(users, "Список пользователей не должен быть пустым");
        assertTrue(users.size() >= 2, "В списке должно быть 2 пользователя");

        boolean containsSveta = users.stream()
                .anyMatch(u -> u.getName().equals("Sveta") && u.getEmail().equals("Sveta@gov.de"));
        assertTrue(containsSveta, "Пользователь Света должен быть в списке");
    }

    @Test
    void when_deleteNonExistentUser_then_doesNotThrowException() {
        Long nonExistentId = 10000L;
        assertDoesNotThrow(() -> userRepository.deleteById(nonExistentId));
    }
}
