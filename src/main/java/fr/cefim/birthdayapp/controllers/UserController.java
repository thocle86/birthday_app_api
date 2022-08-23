package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.dtos.UserDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;
import fr.cefim.birthdayapp.exceptions.UsernameAlreadyExistException;
import fr.cefim.birthdayapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService mUserService;

    public UserController(UserService userService) {
        mUserService = userService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(mUserService.getAllUsers(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(mUserService.getUserById(id), HttpStatus.FOUND);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<User> createByDTO(@RequestBody UserDTO dto) throws UsernameAlreadyExistException {
        return new ResponseEntity<>(mUserService.createByDTO(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateById(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException {
        return new ResponseEntity<>(mUserService.updateById(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws UserNotFoundException {
        mUserService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
