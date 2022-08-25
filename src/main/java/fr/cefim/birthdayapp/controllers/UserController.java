package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.dtos.UserDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl mUserService;

    public UserController(UserServiceImpl userService) {
        mUserService = userService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = mUserService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User userFound = mUserService.getUserById(id).get();
        return new ResponseEntity<>(userFound, HttpStatus.FOUND);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {
        User userSaved = mUserService.saveUserByDTO(userDTO);
        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User userUpdated = mUserService.updateUserByDTO(id, userDTO);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        mUserService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
