package com.bootcamp.quickdemo.mappers.Impl;

import org.springframework.stereotype.Component;

import com.bootcamp.quickdemo.dto.AdmissionRequestDTO;
import com.bootcamp.quickdemo.dto.AdmissionResponseDTO;
import com.bootcamp.quickdemo.model.AdmissionModel;

@Component
public class AdmissionMapper {

    public AdmissionModel toEntity(AdmissionRequestDTO dto) {
        if (dto == null)
            return null;

        return AdmissionModel.builder()
                .patientId(dto.getPatientId())
                .doctorId(dto.getDoctorId())
                .purposeId(dto.getPurposeId())
                .admissionDate(dto.getAdmissionDate())
                .dischargeDate(dto.getDischargeDate())
                .status(dto.getStatus())
                .roomNumber(dto.getRoomNumber())
                .build();
    }

    public AdmissionResponseDTO toResponseDTO(AdmissionModel entity) {
        if (entity == null) {
            return null;
        }

        AdmissionResponseDTO dto = new AdmissionResponseDTO();
        dto.setId(entity.getId());
        dto.setAdmissionDate(entity.getAdmissionDate());
        dto.setDischargeDate(entity.getDischargeDate());
        dto.setStatus(entity.getStatus());
        dto.setRoomNumber(entity.getRoomNumber());

        dto.setPatientId(entity.getPatientId());
        dto.setDoctorId(entity.getDoctorId());
        dto.setPurposeId(entity.getPurposeId());

        return dto;
    }
}
