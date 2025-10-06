package com.bootcamp.quickdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bootcamp.quickdemo.colors.SpanishBlue;
import com.bootcamp.quickdemo.colors.SpanishGreen;
import com.bootcamp.quickdemo.colors.SpanishRed;
import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Tag(name = "Home", description = "Home and test endpoints")
public class HomeController {
    private final SpanishBlue spanishBlue;
    private final SpanishGreen spanishGreen;
    private final SpanishRed spanishRed;

    public HomeController(SpanishBlue spanishBlue, SpanishGreen spanishGreen, SpanishRed spanishRed) {
        this.spanishBlue = spanishBlue;
        this.spanishGreen = spanishGreen;
        this.spanishRed = spanishRed;
    }

    @Operation(summary = "Home endpoint", description = "Returns a welcome message or redirects to documentation")
    @GetMapping("/")
    public RedirectView home() {
        return new RedirectView("/index.html");
    }

    @Operation(summary = "Test color printing", description = "Test endpoint for color printing")
    @GetMapping("/colors")
    public ApiResponse<String> printColor() {
        String result = spanishBlue.printInColor() + ", " + spanishGreen.printInColor() + ", " + spanishRed.printInColor();
        return DefaultResponse.displayFoundObject(result);
    }
    
    @Operation(summary = "Test error handling", description = "Endpoint to test different error scenarios")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/test-error")
    public ApiResponse<String> testError(
            @Parameter(description = "Type of error to simulate", required = true)
            @RequestParam(defaultValue = "none") String type) {
        
        switch (type.toLowerCase()) {
            case "badrequest":
                throw new BadRequestException("This is a simulated bad request error");
            case "notfound":
                throw new ResourceNotFoundException("This is a simulated resource not found error");
            case "servererror":
                throw new RuntimeException("This is a simulated server error");
            case "unauthorized":
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This is a simulated unauthorized error");
            case "forbidden":
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is a simulated forbidden error");
            default:
                return DefaultResponse.displayFoundObject("No error simulation - working normally");
        }
    }
    
    @Operation(summary = "Test path variable validation", description = "Test endpoint for path variable validation")
    @GetMapping("/validate/{id}")
    public ApiResponse<String> validatePathVariable(
            @Parameter(description = "ID to validate", required = true, example = "123")
            @PathVariable Long id) {
        if (id <= 0) {
            throw new BadRequestException("ID must be a positive number");
        }
        return DefaultResponse.displayFoundObject("ID " + id + " is valid");
    }
}
