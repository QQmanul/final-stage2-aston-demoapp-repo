package ru.aston.homework5.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.homework4.controller.UserController;
import ru.aston.homework4.hateoas.UserModelAssembler;
import ru.aston.homework5.service.UserServiceNotifiable;

@RestController
@RequestMapping("/v2/users")
@Tag(name =  "User Notifiable API", description = "API для управления данными о пользователях системы (Уведомляет пользователей по Email)")
public class UserControllerNotifiable extends UserController {
    public UserControllerNotifiable(UserServiceNotifiable userService, UserModelAssembler assembler) {
        super(userService, assembler);
    }
}
