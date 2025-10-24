package com.bootcamp.quickdemo.mappers;

import com.bootcamp.quickdemo.dto.AppointmentRequestDTO;
import com.bootcamp.quickdemo.dto.AppointmentResponseDTO;
import com.bootcamp.quickdemo.model.AppointmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    // Simple mappings - let MapStruct infer the mappings
    AppointmentModel toModel(AppointmentRequestDTO dto);

    AppointmentResponseDTO toDto(AppointmentModel model);

    // Simple update method
    void updateModelFromDto(AppointmentRequestDTO dto, @MappingTarget AppointmentModel model);
}
