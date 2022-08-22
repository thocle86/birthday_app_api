package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.UserNotFound;
import fr.cefim.birthdayapp.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService mUserService;

    public UserController(UserService userService) {
        mUserService = userService;
    }

    @GetMapping(value = {"", "/"})
    public List<User> getUsers() {
        return mUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFound {
        return mUserService.getUserById(id);
    }

}
