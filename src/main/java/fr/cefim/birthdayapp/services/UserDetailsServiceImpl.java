package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.exceptions.BusinessResourceException;
import fr.cefim.birthdayapp.repositories.UserRepository;
import fr.cefim.birthdayapp.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository mUserRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (mUserRepository.findByUsername(username).isEmpty()) {
            throw new BusinessResourceException("UserNotFound", String.format("No user with this username: %s", username), HttpStatus.NOT_FOUND);
        }
        return new UserDetailsImpl(mUserRepository.findByUsername(username).get());
    }

}
