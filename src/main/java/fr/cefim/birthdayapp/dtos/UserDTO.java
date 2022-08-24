package fr.cefim.birthdayapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO implements Serializable {

    private String username;

    private String password;

    private String email;

}
