package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.dtos.LoginDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.services.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {

    private final UserServiceImpl mUserService;

    public LoginController(UserServiceImpl userService) {
        mUserService = userService;
    }

    @PostMapping("/login")
    public UserDetails getLogin(@RequestBody LoginDTO loginDTO) {
        Optional<User> user = mUserService.getUserByCredentials(loginDTO.getUsername(), loginDTO.getPassword());
        return mUserService.loadUserByUsername(user.get().getUsername());
    }

}
