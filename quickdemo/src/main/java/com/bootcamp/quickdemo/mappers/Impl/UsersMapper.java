
package com.bootcamp.quickdemo.mappers.Impl;

import com.bootcamp.quickdemo.dto.UserDTO;
import com.bootcamp.quickdemo.model.UserModel;
import com.bootcamp.quickdemo.mappers.MapperConfig;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper implements MapperConfig<UserModel, UserDTO> {
    @Override
    public UserDTO toDto(UserModel user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
    
    @Override
    public UserModel toEntity(UserDTO dto) {
        return UserModel.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}