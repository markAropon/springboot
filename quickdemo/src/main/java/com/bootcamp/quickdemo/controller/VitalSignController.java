package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.VitalSignRequestDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.VitalSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
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
