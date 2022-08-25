package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.UserDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.BusinessResourceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id) throws BusinessResourceException;

    Optional<User> getUserByUsername(String username) throws BusinessResourceException;

    Optional<User> getUserByCredentials(String username, String password) throws BusinessResourceException;

    User saveUserByDTO(UserDTO userDTO) throws BusinessResourceException;

    User updateUserByDTO(Long id, UserDTO userDTO) throws BusinessResourceException;

    void deleteUserById(Long id) throws BusinessResourceException;

}
