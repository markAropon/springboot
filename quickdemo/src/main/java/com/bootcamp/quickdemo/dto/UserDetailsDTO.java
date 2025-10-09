package com.bootcamp.quickdemo.dto;

import java.time.LocalDateTime;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User details response for authenticated user")
public class UserDetailsDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @Schema(description = "User's username", example = "johndoe")
    private String username;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "User's roles", example = "[\"ROLE_PATIENT\"]")
    private Set<String> roles;

    @Schema(description = "Whether the user account is active", example = "true")
    private boolean isActive;

    @Schema(description = "Account creation date", example = "2023-10-08T10:30:00")
    private LocalDateTime dateCreated;

    @Schema(description = "Last modification date", example = "2023-10-08T10:30:00")
    private LocalDateTime dateModified;
}