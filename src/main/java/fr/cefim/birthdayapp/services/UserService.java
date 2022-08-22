package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.UserNotFound;

import java.util.List;

public interface UserService {

    public User login(String username, String password) throws UserNotFound;

    public List<User> getAllUsers();

    public User getUserById(Long id) throws UserNotFound;

    public User save(User user);

}
