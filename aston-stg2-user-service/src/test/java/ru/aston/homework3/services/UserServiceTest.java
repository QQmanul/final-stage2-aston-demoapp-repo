package ru.aston.homework3.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.homework2.entities.User;
import ru.aston.homework2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void when_create_shouldCallRepository() {
        User vova = new User("Vova", "Vova@gov.de", 21);

        userService.create(vova);

        verify(userRepository).create(vova);
    }

    @Test
    void when_createNullUser_throwException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.create(null)
        );

        verify(userRepository, never()).create(any());
    }

    @Test
    void when_updateUserWithNullId_throwException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.update(new User())
        );

        verify(userRepository, never()).update(any());
    }

    @Test
    void when_deleteUserWithNullId_throwException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.delete(null)
        );

        verifyNoInteractions(userRepository);
    }

    @Test
    void when_findAll_thenReturnList() {
        List<User> users = List.of(
            new User("Vova", "Vova@gov.de", 21),
            new User("Nikita", "Nikita@gov.de", 21)
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void when_findUserById_then_returnUser() {
        User nikita = new User("Nikita", "Nikita@gov.de", 21);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(nikita));

        Optional<User> returnVal = userRepository.findById(1L);

        assertTrue(returnVal.isPresent());
        assertEquals(returnVal.get(), nikita);

        verify(userRepository).findById(1L);
    }

    @Test
    void when_findUserById_throwException_whenNotFound() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> userService.findById(1L)
        );
    }
}