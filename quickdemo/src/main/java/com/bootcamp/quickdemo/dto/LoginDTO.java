package com.bootcamp.quickdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Username or email is required")
    @Size(min = 4, max = 100, message = "Username or email must be between 4 and 100 characters")
    @Schema(example = "john.doe@example.com")
    private String usernameOrEmail;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be at least 8 characters")
    @Schema(example = "password123")
    private String password;
}
