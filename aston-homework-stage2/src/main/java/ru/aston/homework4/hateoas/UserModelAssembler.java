package ru.aston.homework4.hateoas;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import ru.aston.homework4.controller.UserController;
import ru.aston.homework4.dto.response.UserDTO;

@Component
public class UserModelAssembler
        implements RepresentationModelAssembler<UserDTO, UserModel> {

    @Override
    public UserModel toModel(UserDTO user) {

        UserModel model = new UserModel(user);

        model.add(
                linkTo(methodOn(UserController.class)
                        .getById(user.id()))
                        .withSelfRel()
        );

        model.add(
                linkTo(methodOn(UserController.class)
                        .getAll())
                        .withRel("users")
        );

        return model;
    }
}
