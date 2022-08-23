package fr.cefim.birthdayapp.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
        HttpStatus status = HttpStatus.NOT_FOUND;
    }

}
