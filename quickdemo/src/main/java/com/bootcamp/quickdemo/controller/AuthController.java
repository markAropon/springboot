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
    @PostMapping("/register")
    public ApiResponse<String> registration(@Valid @RequestBody UserRegistrationDTO userRegistrationDto){
        String register = authService.userRegistration(userRegistrationDto);
        return DefaultResponse.displayCreatedObject(register);
    } 
    @Operation(summary = "Login a user", description = "Endpoint for user login")
    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        AuthResponseDTO response = authService.login(loginDto);
        return DefaultResponse.displayCreatedObject(response);
    }
}