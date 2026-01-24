package ru.aston.homework4.hateoas;

import org.springframework.hateoas.RepresentationModel;
import ru.aston.homework4.dto.response.UserDTO;

public class UserModel extends RepresentationModel<UserModel> {
    private final UserDTO user;

    public UserModel(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
