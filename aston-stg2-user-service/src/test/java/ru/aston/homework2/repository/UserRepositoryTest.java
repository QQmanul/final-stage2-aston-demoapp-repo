package ru.aston.homework2.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.homework2.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    private static SessionFactory sessionFactory;
    private UserRepository userRepository;

    @BeforeAll
    static void init() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.url", postgres.getJdbcUrl());
        properties.put("hibernate.connection.username", postgres.getUsername());
        properties.put("hibernate.connection.password", postgres.getPassword());

        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.show_sql", "true");

        sessionFactory = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createMutationQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @AfterAll
    static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void when_createUser_then_idIsGenerated() {
        User vova = new User("Vova", "Vova@gov.de", 21);

        userRepository.create(vova);

        assertNotNull(vova.getId(), "ID не присвоен");
    }

    @Test
    void when_createValidUser_then_userIsSavedToDatabase() {
        User vova = new User("Vova", "Vova@gov.de", 21);

        userRepository.create(vova);

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
        userRepository.create(nikita);

        Long id = nikita.getId();
        assertTrue(userRepository.findById(id).isPresent(), "Пользователь не создался");

        userRepository.delete(id);

        Optional<User> deletedUser = userRepository.findById(id);
        assertTrue(deletedUser.isEmpty(), "Пользователь не удалился");
    }

    @Test
    void when_updateUserAge_then_changesArePersisted() {
        User sveta = new User("Sveta", "Sveta@gov.de", 21);
        userRepository.create(sveta);
        Long id = sveta.getId();

        int newAge = 19;
        sveta.setAge(newAge);

        userRepository.update(sveta);

        User updatedUser = userRepository.findById(id).orElseThrow();
        assertEquals(newAge, updatedUser.getAge(), "Возраст не соответствует значению");
    }

    @Test
    void when_findAll_then_returnAllSavedUsers() {
        User sveta = new User("Sveta", "Sveta@gov.de", 21);
        User nikita = new User("Nikita", "Nikita@gov.de", 21);

        userRepository.create(sveta);
        userRepository.create(nikita);

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
        assertDoesNotThrow(() -> userRepository.delete(nonExistentId));
    }
}