package com.bootcamp.quickdemo.mappers.Impl;

import com.bootcamp.quickdemo.dto.DoctorRequestDTO;
import com.bootcamp.quickdemo.dto.DoctorResponseDTO;
import com.bootcamp.quickdemo.mappers.MapperConfig;
import com.bootcamp.quickdemo.model.DoctorModel;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper implements MapperConfig<DoctorModel, DoctorResponseDTO> {

    public DoctorResponseDTO toDto(DoctorModel entity) {
        return DoctorResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .specialty(entity.getSpecialty())
                .build();
    }

    public DoctorModel toEntity(DoctorResponseDTO dto) {
        return DoctorModel.builder()
                .id(dto.getId())
                .name(dto.getName())
                .specialty(dto.getSpecialty())
                .build();
    }
    
    public DoctorModel requestDtoToEntity(DoctorRequestDTO dto) {
        return DoctorModel.builder()
                .name(dto.getName())
                .specialty(dto.getSpecialty())
                .build();
    }
    
    public DoctorModel requestDtoToEntity(DoctorRequestDTO dto, DoctorModel existingEntity) {
        existingEntity.setName(dto.getName());
        existingEntity.setSpecialty(dto.getSpecialty());
        return existingEntity;
    }
}
