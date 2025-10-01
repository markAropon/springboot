package com.bootcamp.quickdemo.exception;

import java.time.LocalDateTime;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

@Hidden
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ApiResponse<ErrorDetails>> handleError(HttpServletRequest request, WebRequest webRequest) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        Object message = request.getAttribute("jakarta.servlet.error.message");
        Object path = request.getAttribute("jakarta.servlet.error.request_uri");
        
        int statusCode = status != null ? (Integer) status : 500;
        String errorMessage = message != null ? message.toString() : "Unknown error";
        String requestPath = path != null ? path.toString() : request.getRequestURI();
        
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                errorMessage,
                requestPath,
                httpStatus.name()
        );
        ApiResponse<ErrorDetails> apiResponse;
        
        switch (httpStatus) {
            case NOT_FOUND:
                apiResponse = DefaultResponse.displayNotFoundObject(errorDetails);
                break;
            case BAD_REQUEST:
                apiResponse = DefaultResponse.displayBadRequestObject(errorDetails);
                break;
            case UNAUTHORIZED:
                apiResponse = DefaultResponse.displayUnauthorizedObject(errorDetails);
                break;
            case FORBIDDEN:
                apiResponse = DefaultResponse.displayForbiddenObject(errorDetails);
                break;
            case METHOD_NOT_ALLOWED:
                apiResponse = DefaultResponse.displayMethodNotAllowedObject(errorDetails);
                break;
            default:
                apiResponse = DefaultResponse.displayServerErrorObject(errorDetails);
        }
        
        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}


// :8081/api-docs/swagger-config:1  Failed to load resource: the server responded with a status of 401 ()
// swagger-ui-standalone-preset.js:2 undefined /api-docs/swagger-config