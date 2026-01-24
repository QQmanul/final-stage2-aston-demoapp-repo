package ru.aston.homework4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.homework4.dto.request.UserCreateDTO;
import ru.aston.homework4.dto.response.UserDTO;
import ru.aston.homework4.hateoas.UserModel;
import ru.aston.homework4.hateoas.UserModelAssembler;
import ru.aston.homework4.service.UserService;

@RestController
@RequestMapping("/users")
@Tag(name =  "User API", description = "API для управления данными о пользователях системы")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler assembler;

    public UserController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @Operation(summary = "Создать нового пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateDTO createDTO) {
        var response = userService.create(createDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить список пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей получен")
    @GetMapping
    public CollectionModel<UserModel> getAll() {
        return assembler.toCollectionModel(userService.findAll());
    }

    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает объект пользователя, если он найден в системе."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public UserModel getById(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id) {
        return assembler.toModel(userService.findById(id));
    }

    @Operation(summary = "Обновить пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь обновлён"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping("/{id}")
    public UserDTO update(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UserCreateDTO createDTO) {
        return userService.update(id, createDTO);
    }

    @Operation(summary = "Удалить пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id
    ) {
        userService.delete(id);
    }

}
