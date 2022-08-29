package fr.cefim.birthdayapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {

    private String username;

    private String password;

    private String email;

    public UserDTO() {}

}
