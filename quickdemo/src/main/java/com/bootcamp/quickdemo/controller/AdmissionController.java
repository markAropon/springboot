package com.bootcamp.quickdemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.AdmissionRequestDTO;
import com.bootcamp.quickdemo.dto.AdmissionResponseDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.AdmissionService;
import com.bootcamp.quickdemo.services.VitalSignService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admissions")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;
    private final VitalSignService vitalSignService;

    @PostMapping
    public ApiResponse<AdmissionResponseDTO> createAdmission(@Valid @RequestBody AdmissionRequestDTO dto) {
        return DefaultResponse.displayCreatedObject(admissionService.createAdmission(dto));
    }

    @GetMapping
    public ApiResponse<List<AdmissionResponseDTO>> getAllAdmissions() {
        List<AdmissionResponseDTO> admissions = admissionService.getAllAdmissions();
        if (admissions.isEmpty()) {
            throw new ResourceNotFoundException("No admissions found.");
        }
        return DefaultResponse.displayFoundObject(admissions);
    }

    @GetMapping("/{id}")
    public ApiResponse<AdmissionResponseDTO> getAdmissionById(@PathVariable Integer id) {
        Optional<AdmissionResponseDTO> admission = (admissionService.getAdmissionById(id));
        return admission.map(DefaultResponse::displayFoundObject)
                .orElseThrow(() -> new ResourceNotFoundException("Admission with ID " + id + " not found."));
    }

    @PutMapping("/{id}")
    public ApiResponse<AdmissionResponseDTO> updateAdmission(
            @PathVariable Integer id,
            @Valid @RequestBody AdmissionRequestDTO dto
    ) {
        AdmissionResponseDTO updated = admissionService.updateAdmission(id, dto);
        if (updated == null) {
            throw new ResourceNotFoundException("Cannot update admission. ID " + id + " not found.");
        }
        return DefaultResponse.displayUpdatedObject(updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAdmission(@PathVariable Integer id) {
        admissionService.deleteAdmission(id);
        return DefaultResponse.displayDeletedObject(null);
    }

    @GetMapping("/{id}/vital-signs")
    public ApiResponse<List<VitalSignResponseDTO>> getAdmissionVitalSigns(@PathVariable Integer id) {
        List<VitalSignResponseDTO> vitalSigns = vitalSignService.getVitalSignsByAdmission(id);
        if (vitalSigns.isEmpty()) {
            throw new ResourceNotFoundException("No vital signs found for admission ID " + id + ".");
        }
        return DefaultResponse.displayFoundObject(vitalSigns);
    }

    // Additional endpoints for diagnoses, prescriptions, treatments, etc.
    // would be implemented here following the same pattern
}
