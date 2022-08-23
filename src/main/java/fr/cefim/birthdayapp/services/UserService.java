package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.UserDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.AccessDeniedException;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;
import fr.cefim.birthdayapp.exceptions.UsernameAlreadyExistException;

import java.util.List;

public interface UserService {

    public User login(String username, String password) throws UserNotFoundException;

    public List<User> getAllUsers();

    public User getUserById(Long id) throws UserNotFoundException, AccessDeniedException;

    public User getUserByUsername(String username);

    User createByDTO(UserDTO dto) throws UsernameAlreadyExistException;

    User updateById(Long id, User user) throws UserNotFoundException, AccessDeniedException;

    void deleteById(Long id) throws UserNotFoundException, AccessDeniedException;

}
