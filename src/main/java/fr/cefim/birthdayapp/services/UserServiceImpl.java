package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.UserDTO;
import fr.cefim.birthdayapp.entities.Role;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.BusinessResourceException;
import fr.cefim.birthdayapp.models.RoleEnum;
import fr.cefim.birthdayapp.repositories.RoleRepository;
import fr.cefim.birthdayapp.repositories.UserRepository;
import fr.cefim.birthdayapp.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;

    private final RoleRepository mRoleRepository;

    private final BCryptPasswordEncoder mBCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        mUserRepository = userRepository;
        mRoleRepository = roleRepository;
        mBCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return mUserRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) throws BusinessResourceException {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(id)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with this ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userFound = mUserRepository.findById(id);
        if (userFound.isEmpty()) {
            throw new BusinessResourceException("UserNotFound", String.format("No user with this ID: %d", id), HttpStatus.NOT_FOUND);
        }
        return userFound;
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws BusinessResourceException {
        Optional<User> userFound = mUserRepository.findByUsername(username);
        if (userFound.isEmpty()) {
            throw new BusinessResourceException("UserNotFound", String.format("No user with this username: %s", username), HttpStatus.NOT_FOUND);
        }
        return userFound;
    }

    @Override
    public Optional<User> getUserByCredentials(String username, String password) throws BusinessResourceException {
        Optional<User> userFound = mUserRepository.findByUsername(username);
        if (userFound.isEmpty()) {
            throw new BusinessResourceException("UserNotFound", String.format("No user with this username: %s", username), HttpStatus.NOT_FOUND);
        } else {
            if (!mBCryptPasswordEncoder.matches(password, userFound.get().getPassword())) {
                throw new BusinessResourceException("UserNotFound", "No user with this password", HttpStatus.NOT_FOUND);
            }
        }
        return userFound;
    }

    @Override
    public User saveUserByDTO(UserDTO userDTO) throws BusinessResourceException {
        if (mUserRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new BusinessResourceException("UsernameAlreadyExist", String.format("User already exist with this username: %s", userDTO.getUsername()), HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(mBCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(mRoleRepository.findByName(RoleEnum.ROLE_USER).stream().findFirst().orElseThrow());
        user.setRoles(roleSet);
        mUserRepository.save(user);
        return user;
    }

    @Override
    public User updateUserByDTO(Long id, UserDTO userDTO) throws BusinessResourceException {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(id)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userFound = mUserRepository.findById(id);
        User userUpdated = new User();
        if (userFound.isEmpty()) {
            throw new BusinessResourceException("UserNotFound", String.format("No user with this ID: %d", id), HttpStatus.NOT_FOUND);
        } else {
            userUpdated = userFound.get();
            userUpdated.setUsername(userDTO.getUsername());
            userUpdated.setPassword(mBCryptPasswordEncoder.encode(userDTO.getPassword()));
            userUpdated.setEmail(userDTO.getEmail());
        }
        return mUserRepository.save(userUpdated);
    }

    @Override
    public void deleteUserById(Long id) {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(id)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        try {
            mUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessResourceException("UserNotFound", String.format("No user with this ID: %d", id), HttpStatus.NOT_FOUND);
        }
    }

}
