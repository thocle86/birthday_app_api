package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.dtos.LoginDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;
import fr.cefim.birthdayapp.services.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserServiceImpl mUserService;

    public LoginController(UserServiceImpl userService) {
        mUserService = userService;
    }

    @PostMapping("/login")
    public UserDetails getLogin(@RequestBody LoginDTO loginDTO) throws UserNotFoundException {
        User user = mUserService.login(loginDTO.getUsername(), loginDTO.getPassword());
        return mUserService.loadUserByUsername(user.getUsername());
    }

}
