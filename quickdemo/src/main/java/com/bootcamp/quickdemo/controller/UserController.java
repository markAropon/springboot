package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.model.UserModel;
import com.bootcamp.quickdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ApiResponse<List<UserModel>> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }
        return DefaultResponse.displayFoundObject(users);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserModel> getUserById(@PathVariable Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
        return DefaultResponse.displayFoundObject(user);
    }

    @PostMapping
    public ApiResponse<UserModel> createUser(@RequestBody UserModel user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new BadRequestException("Email cannot be empty");
        }
        UserModel saved = userRepository.save(user);
        return DefaultResponse.displayCreatedObject(saved);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel userDetails) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        if (userDetails.getEmail() == null || userDetails.getEmail().isEmpty()) {
            throw new BadRequestException("Email cannot be empty");
        }

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        UserModel updated = userRepository.save(user);

        return DefaultResponse.displayUpdatedObject(updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
        return DefaultResponse.displayDeletedObject(null);
    }
}
