package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.UserNotFound;
import fr.cefim.birthdayapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;

    public UserServiceImpl(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public User login(String username, String password) throws UserNotFound {
        return mUserRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new UserNotFound(String.format("User not found with this credentials => username: %s, password: %s", username, password)));
    }

    @Override
    public List<User> getAllUsers() {
        return mUserRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFound {
        return mUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(String.format("User not found with this id: %d", id)));
    }

    @Override
    public User save(User user) {
        return mUserRepository.save(user);
    }

}
