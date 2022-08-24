package fr.cefim.birthdayapp.exceptions;

import org.springframework.http.HttpStatus;

public class BirthdayNotFoundException extends Exception {

    public BirthdayNotFoundException(String message) {
        super(message);
        HttpStatus status = HttpStatus.NOT_FOUND;
    }

}
