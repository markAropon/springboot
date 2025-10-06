package com.bootcamp.quickdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.AuthResponseDTO;
import com.bootcamp.quickdemo.dto.LoginDTO;
import com.bootcamp.quickdemo.dto.UserRegistrationDTO;
import com.bootcamp.quickdemo.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "[PUBLIC] User registration and authentication endpoints")
public class AuthController {
    private final AuthService authService;
    
    @Operation(summary = "Register a new user", description = "Endpoint for user registration")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", 
            description = "User successfully registered",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid input or username/email already in use",
            content = @Content(mediaType = "application/json")
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json")
        )
    })
    @PostMapping("/register")
    public ApiResponse<String> registration(
            @Parameter(description = "Registration details", required = true)
            @Valid @RequestBody UserRegistrationDTO userRegistrationDto){
        String register = authService.userRegistration(userRegistrationDto);
        return DefaultResponse.displayCreatedObject(register);
    } 
    
    @Operation(summary = "Login a user", description = "Endpoint for user login to obtain JWT token")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Login successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid credentials",
            content = @Content(mediaType = "application/json")
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401", 
            description = "Authentication failed",
            content = @Content(mediaType = "application/json")
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json")
        )
    })
    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO> login(
            @Parameter(description = "Login credentials", required = true)
            @Valid @RequestBody LoginDTO loginDto) {
        AuthResponseDTO response = authService.login(loginDto);
        return DefaultResponse.displayCreatedObject(response);
    }
}