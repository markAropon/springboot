package com.bootcamp.quickdemo.exception;

/**
 * Exception thrown when a conflict occurs in business operations
 * (e.g., duplicate entry, concurrent modification, etc.)
 */
public class ConflictException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ConflictException(String message) {
        super(message);
    }
    
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
