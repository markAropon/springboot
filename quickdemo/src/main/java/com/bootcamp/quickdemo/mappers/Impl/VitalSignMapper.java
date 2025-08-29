package com.bootcamp.quickdemo.mappers.Impl;

import com.bootcamp.quickdemo.dto.VitalSignRequestDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import com.bootcamp.quickdemo.model.VitalSignModel;
import org.springframework.stereotype.Component;

@Component
public class VitalSignMapper {

    public VitalSignModel toEntity(VitalSignRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return VitalSignModel.builder()
                .admissionId(dto.getAdmissionId())
                .recordedDate(dto.getRecordedDate())
                .temperature(dto.getTemperature())
                .bloodPressure(dto.getBloodPressure())
                .heartRate(dto.getHeartRate())
                .respiratoryRate(dto.getRespiratoryRate())
                .spo2(dto.getSpo2())
                .weight(dto.getWeight())
                .height(dto.getHeight())
                .build();
    }
    
    public VitalSignResponseDTO toDto(VitalSignModel entity) {
        if (entity == null) {
            return null;
        }
        
        return VitalSignResponseDTO.builder()
                .id(entity.getId())
                .admissionId(entity.getAdmissionId())
                .recordedDate(entity.getRecordedDate())
                .temperature(entity.getTemperature())
                .bloodPressure(entity.getBloodPressure())
                .heartRate(entity.getHeartRate())
                .respiratoryRate(entity.getRespiratoryRate())
                .spo2(entity.getSpo2())
                .weight(entity.getWeight())
                .height(entity.getHeight())
                .build();
    }
}
