package com.bootcamp.quickdemo.mappers.Impl;

import com.bootcamp.quickdemo.dto.UserDTO;
import com.bootcamp.quickdemo.model.Users;
import com.bootcamp.quickdemo.mappers.MapperConfig;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper implements MapperConfig<Users, UserDTO> {
    @Override
    public UserDTO toDto(Users user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
    
    @Override
    public Users toEntity(UserDTO dto) {
        return Users.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(dto.getEmail()) // Using email as username for simplicity
                .password("") // Empty password, should be handled by service layer
                .isActive(true) // Default to active
                .build();
    }
}