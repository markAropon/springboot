package com.bootcamp.quickdemo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.common.RateLimit;
import com.bootcamp.quickdemo.dto.PatientInsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.PatientInsuranceResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.PatientInsuranceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patient-insurances")
@Tag(name = "Patient Insurance", description = "[PATIENT, DOCTOR, ADMIN] Patient insurance management endpoints")
@RequiredArgsConstructor
@RateLimit(limit = 3, durationSeconds = 15)
public class PatientInsuranceController {

    private final PatientInsuranceService patientInsuranceService;

    @GetMapping("/{id}")
    public ApiResponse<PatientInsuranceResponseDTO> getPatientInsuranceById(@PathVariable Long id) {
        PatientInsuranceResponseDTO response = patientInsuranceService.getPatientInsuranceById(id);
        if (response == null) {
            throw new ResourceNotFoundException("Patient insurance with ID " + id + " not found.");
        }
        return DefaultResponse.displayFoundObject(response);
    }

    @PostMapping
    public ApiResponse<PatientInsuranceResponseDTO> createPatientInsurance(
            @Valid @RequestBody PatientInsuranceRequestDTO dto) {
        PatientInsuranceResponseDTO created = patientInsuranceService.createPatientInsurance(dto);
        return DefaultResponse.displayCreatedObject(created);
    }

    @PutMapping("/{id}")
    public ApiResponse<PatientInsuranceResponseDTO> updatePatientInsurance(
            @PathVariable Long id,
            @Valid @RequestBody PatientInsuranceRequestDTO dto) {
        PatientInsuranceResponseDTO updated = patientInsuranceService.updatePatientInsurance(id, dto);
        if (updated == null) {
            throw new ResourceNotFoundException("Cannot update. Patient insurance with ID " + id + " not found.");
        }
        return DefaultResponse.displayUpdatedObject(updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePatientInsurance(@PathVariable Long id) {
        patientInsuranceService.deletePatientInsurance(id);
        return DefaultResponse.displayDeletedObject(null);
    }
}
