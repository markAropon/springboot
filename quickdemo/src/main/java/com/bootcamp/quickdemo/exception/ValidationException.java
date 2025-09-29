package com.bootcamp.quickdemo.exception;

/**
 * Exception thrown when validation fails for a data entity.
 * This is separate from standard bean validation and is
 * for business logic validation.
 */
public class ValidationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
