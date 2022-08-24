package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.UserDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.AccessDeniedException;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;
import fr.cefim.birthdayapp.exceptions.UsernameAlreadyExistException;
import fr.cefim.birthdayapp.repositories.UserRepository;
import fr.cefim.birthdayapp.security.MyPrincipalUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository mUserRepository;

    private final PasswordEncoder mPasswordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        mUserRepository = userRepository;
        mPasswordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) throws UserNotFoundException {
        Optional<User> user = mUserRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User not found with this username: %s", username));
        }
        if (!mPasswordEncoder.matches(password, user.get().getPassword())) {
            throw new UserNotFoundException(String.format("User not found with this password: %s", password));
        }
        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return mUserRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException, AccessDeniedException {
        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(id)) {
            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), id));
        }
        return mUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found with this id: %d", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return mUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username: %s", username)));
    }

    @Override
    public User createByDTO(UserDTO dto) throws UsernameAlreadyExistException {
        if (mUserRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(String.format("Username already exist: %s", dto.getUsername()));
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(mPasswordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        mUserRepository.save(user);
        return user;
    }

    @Override
    public User updateById(Long id, User user) throws UserNotFoundException, AccessDeniedException {
        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(id)) {
            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), id));
        }
        return mUserRepository.findById(id)
                .map(userUpdated -> {
                    userUpdated.setUsername(user.getUsername());
                    userUpdated.setPassword(mPasswordEncoder.encode(user.getPassword()));
                    userUpdated.setEmail(user.getEmail());
                    return mUserRepository.save(userUpdated);
                }).orElseThrow(() -> new UserNotFoundException(String.format("User not found with this id: %d", id)));
    }

    @Override
    public void deleteById(Long id) throws AccessDeniedException {
        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(id)) {
            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), id));
        }
        mUserRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyPrincipalUser(getUserByUsername(username));
    }

}
