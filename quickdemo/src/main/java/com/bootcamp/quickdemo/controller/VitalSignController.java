package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.dto.VitalSignRequestDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import com.bootcamp.quickdemo.services.VitalSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VitalSignController {

    private final VitalSignService vitalSignService;

    @PostMapping("/admissions/{admissionId}/vital-signs")
    public ResponseEntity<VitalSignResponseDTO> createVitalSign(
            @PathVariable Integer admissionId,
            @RequestBody VitalSignRequestDTO dto) {
        dto.setAdmissionId(admissionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(vitalSignService.createVitalSign(dto));
    }

    @GetMapping("/vital-signs/{id}")
    public ResponseEntity<VitalSignResponseDTO> getVitalSignById(@PathVariable Long id) {
        return ResponseEntity.ok(vitalSignService.getVitalSignById(id));
    }
}
