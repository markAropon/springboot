package com.bootcamp.quickdemo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bootcamp.quickdemo.common.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // See HTTP response status codes:
    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status

    /**
     * Get the current HTTP request path.
     */
    private String getCurrentPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        return request != null ? request.getRequestURI() : "N/A";
    }

    /**
     * Handles MethodArgumentNotValidException for validation errors and returns a
     * 400 Bad Request status.
     *
     * @param ex the MethodArgumentNotValidException thrown when method arguments
     *           fail validation
     * @return ResponseEntity containing an ApiResponse with error details and an
     *         HTTP status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleArgumentMethod(MethodArgumentNotValidException ex) {
        // Create the error details
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Create the ApiResponse object
        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Validation failed for one or more arguments.");
        response.setPayload(errors);
        response.setErrors(List.of(errors));
        response.setErrorCode(400);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        response.setMessage(ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(404);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Bad Request: " + ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(403);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedException(UnauthorizedException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        response.setMessage("Invalid action: " + ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(403);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.FORBIDDEN);
        response.setMessage("You do not have permission to perform this action.");
        response.setPayload(null);
        response.setErrorCode(403);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ForbiddenRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleForbiddenException(ForbiddenRequestException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.FORBIDDEN);
        response.setMessage("Access Denied: " + ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(403);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Illegal argument: " + ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(400);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Malformed JSON request.");
        response.setPayload(null);
        response.setErrorCode(400);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Missing required parameter: " + ex.getParameterName());
        response.setPayload(null);
        response.setErrorCode(400);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> handleBadCredentials(BadCredentialsException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Bad Credentials! Please check your details.");
        response.setPayload(null);
        response.setErrorCode(401);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(ValidationException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Validation Error: " + ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(400);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<String>> handleConflictException(ConflictException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.CONFLICT);
        response.setMessage("Conflict Error: " + ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(409);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        response.setMessage(ex.getMessage());
        response.setPayload(null);
        response.setErrorCode(500);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(getCurrentPath());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, String>> handleTokenExpired(TokenExpiredException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "TokenExpired");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<String> handleRateLimitExceeded(RateLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

}