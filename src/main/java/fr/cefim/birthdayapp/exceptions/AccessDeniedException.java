package fr.cefim.birthdayapp.exceptions;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends Exception {

    private HttpStatus status;

    public AccessDeniedException(String message) {
        super(message);
        status = HttpStatus.FORBIDDEN;
    }

}
