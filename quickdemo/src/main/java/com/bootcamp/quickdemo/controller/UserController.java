package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.model.Users;
import com.bootcamp.quickdemo.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "[ADMIN] User management endpoints")
public class UserController {

    private final UserRepository userRepository;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No users found")
    })
    @GetMapping
    public ApiResponse<List<Users>> getAllUsers() {
        List<Users> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }
        return DefaultResponse.displayFoundObject(users);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ApiResponse<Users> getUserById(
        @Parameter(description = "ID of the user to retrieve", required = true, example = "1")
        @PathVariable Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
        return DefaultResponse.displayFoundObject(user);
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ApiResponse<Users> createUser(
        @Parameter(description = "User details", required = true)
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object that needs to be added", 
            content = @Content(schema = @Schema(implementation = Users.class)))
        @RequestBody Users user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new BadRequestException("Email cannot be empty");
        }
        Users saved = userRepository.save(user);
        return DefaultResponse.displayCreatedObject(saved);
    }

    @Operation(summary = "Update user", description = "Update an existing user's information")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ApiResponse<Users> updateUser(
        @Parameter(description = "ID of the user to update", required = true, example = "1")
        @PathVariable Long id, 
        @Parameter(description = "Updated user details", required = true)
        @RequestBody Users userDetails) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        if (userDetails.getEmail() == null || userDetails.getEmail().isEmpty()) {
            throw new BadRequestException("Email cannot be empty");
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        Users updated = userRepository.save(user);

        return DefaultResponse.displayUpdatedObject(updated);
    }

    @Operation(summary = "Delete user", description = "Remove a user from the system")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(
        @Parameter(description = "ID of the user to delete", required = true, example = "1")
        @PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
        return DefaultResponse.displayDeletedObject(null);
    }
}
