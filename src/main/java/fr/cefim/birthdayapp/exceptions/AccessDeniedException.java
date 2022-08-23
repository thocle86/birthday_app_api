package fr.cefim.birthdayapp.exceptions;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends RuntimeException {

    private HttpStatus status;

    public AccessDeniedException(String message) {
        super(message);
        status = HttpStatus.FORBIDDEN;
    }

}
