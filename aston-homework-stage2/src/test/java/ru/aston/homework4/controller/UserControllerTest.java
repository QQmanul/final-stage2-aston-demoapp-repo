package ru.aston.homework4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.aston.homework4.dto.request.UserCreateDTO;
import ru.aston.homework4.dto.response.UserDTO;
import ru.aston.homework4.exeption.GlobalExceptionHandler;
import ru.aston.homework4.exeption.UserNotFoundException;
import ru.aston.homework4.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_createUser_then_success() throws Exception  {
        UserCreateDTO request = new UserCreateDTO(
                "Nikita",
                "nikita@test.com",
                22
        );

        UserDTO response = new UserDTO(
                1L,
                "Nikita",
                "nikita@test.com",
                22,
                LocalDateTime.now()
        );

        Mockito.when(userService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Nikita"))
            .andExpect(jsonPath("$.email").value("nikita@test.com"))
            .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    void when_createUser_then_validationError() throws Exception {
        UserCreateDTO request = new UserCreateDTO(
                "",
                "bad-email",
                -5
        );

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Ошибка валидации"))
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.email").exists())
                .andExpect(jsonPath("$.errors.age").exists());
    }

    @Test
    void when_getAllUsers_then_success() throws Exception {
        List<UserDTO> users = List.of(
                new UserDTO(1L, "Ivan", "ivan@test.com", 30, LocalDateTime.now()),
                new UserDTO(2L, "Petr", "petr@test.com", 25, LocalDateTime.now())
        );

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ivan"))
                .andExpect(jsonPath("$[1].name").value("Petr"));
    }
    @Test
    void when_getById_then_success() throws Exception {
        UserDTO user = new UserDTO(
                1L,
                "Ivan",
                "ivan@test.com",
                30,
                LocalDateTime.now()
        );

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@test.com"));
    }

    @Test
    void when_getById_then_notFound() throws Exception {
        when(userService.findById(99L))
                .thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("User не найден с данным ИД: 99"));
    }

    @Test
    void when_updateUser_then_success() throws Exception {
        UserCreateDTO request = new UserCreateDTO(
                "NewName",
                "new@test.com",
                40
        );

        UserDTO response = new UserDTO(
                1L,
                "NewName",
                "new@test.com",
                40,
                LocalDateTime.now()
        );

        when(userService.update(eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewName"))
                .andExpect(jsonPath("$.email").value("new@test.com"))
                .andExpect(jsonPath("$.age").value(40));
    }

    @Test
    void when_deleteUser_then_success() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void when_deleteUser_then_notFound() throws Exception {
        doThrow(new UserNotFoundException(10L))
                .when(userService).delete(10L);

        mockMvc.perform(delete("/users/10"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("User не найден с данным ИД: 10"));
    }
}