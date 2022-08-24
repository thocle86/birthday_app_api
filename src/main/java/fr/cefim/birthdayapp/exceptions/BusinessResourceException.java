package fr.cefim.birthdayapp.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessResourceException extends RuntimeException {

    private Long resourceId;

    private String errorCode;

    private HttpStatus status;

    public BusinessResourceException(String message) {
        super(message);
    }

    public BusinessResourceException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }

    public BusinessResourceException(Long resourceId, String errorCode, String message) {
        super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }

    public BusinessResourceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessResourceException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
