package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.AuthResponseDTO;
import com.bootcamp.quickdemo.dto.LoginDTO;
import com.bootcamp.quickdemo.dto.UserRegistrationDTO;

public interface AuthService {
    String userRegistration(UserRegistrationDTO userRegistrationDTO);
    AuthResponseDTO login(LoginDTO loginDTO);
}
