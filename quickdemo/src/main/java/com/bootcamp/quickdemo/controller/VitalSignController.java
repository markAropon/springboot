package com.bootcamp.quickdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.common.RateLimit;
import com.bootcamp.quickdemo.dto.VitalSignRequestDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.VitalSignService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Vital Signs", description = "[PATIENT, DOCTOR, ADMIN] Patient vital signs management endpoints")
@RateLimit(limit = 3, durationSeconds = 15)
public class VitalSignController {

    private final VitalSignService vitalSignService;

    @PostMapping("/admissions/{admissionId}/vital-signs")
    public ApiResponse<VitalSignResponseDTO> createVitalSign(
            @PathVariable Integer admissionId,
            @RequestBody VitalSignRequestDTO dto) {
        dto.setAdmissionId(admissionId);
        VitalSignResponseDTO created = vitalSignService.createVitalSign(dto);
        return DefaultResponse.displayCreatedObject(created);
    }

    @GetMapping("/vital-signs/{id}")
    public ApiResponse<VitalSignResponseDTO> getVitalSignById(@PathVariable Long id) {
        VitalSignResponseDTO vitalSign = vitalSignService.getVitalSignById(id);
        if (vitalSign == null) {
            throw new ResourceNotFoundException("Vital sign with ID " + id + " not found.");
        }
        return DefaultResponse.displayFoundObject(vitalSign);
    }
}
