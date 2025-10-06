package com.bootcamp.quickdemo.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A class to represent error details in API responses.
 */
@Schema(description = "Error details for API responses")
public class ErrorDetails {

    @Schema(description = "Timestamp when the error occurred", example = "2025-10-01T12:34:56")
    private LocalDateTime timestamp;
    
    @Schema(description = "Error message", example = "Resource not found")
    private String message;
    
    @Schema(description = "Request path that caused the error", example = "/api/patients/123")
    private String path;
    
    @Schema(description = "Error code or status", example = "NOT_FOUND")
    private String errorCode;

    /**
     * Default constructor
     */
    public ErrorDetails() {
    }

    /**
     * Constructor with all fields
     * 
     * @param timestamp When the error occurred
     * @param message The error message
     * @param path The request path
     * @param errorCode The error code or status
     */
    public ErrorDetails(LocalDateTime timestamp, String message, String path, String errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}