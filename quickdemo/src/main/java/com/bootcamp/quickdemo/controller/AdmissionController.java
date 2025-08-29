package com.bootcamp.quickdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.quickdemo.dto.AdmissionRequestDTO;
import com.bootcamp.quickdemo.dto.AdmissionResponseDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import com.bootcamp.quickdemo.services.AdmissionService;
import com.bootcamp.quickdemo.services.VitalSignService;

import java.util.List;

@RestController
@RequestMapping("/admissions")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;
    private final VitalSignService vitalSignService;

    @PostMapping
    public ResponseEntity<AdmissionResponseDTO> createAdmission(@RequestBody AdmissionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(admissionService.createAdmission(dto));
    }
    
    @GetMapping
    public ResponseEntity<List<AdmissionResponseDTO>> getAllAdmissions() {
        return ResponseEntity.ok(admissionService.getAllAdmissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionResponseDTO> getAdmissionById(@PathVariable Integer id) {
        return ResponseEntity.ok(admissionService.getAdmissionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdmissionResponseDTO> updateAdmission(@PathVariable Integer id, @RequestBody AdmissionRequestDTO dto) {
        return ResponseEntity.ok(admissionService.updateAdmission(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmission(@PathVariable Integer id) {
        admissionService.deleteAdmission(id);
        return ResponseEntity.noContent().build();
    }
    
    // Admission-related endpoints
    @GetMapping("/{id}/vital-signs")
    public ResponseEntity<List<VitalSignResponseDTO>> getAdmissionVitalSigns(@PathVariable Integer id) {
        return ResponseEntity.ok(vitalSignService.getVitalSignsByAdmission(id));
    }
    
    // Additional endpoints for diagnoses, prescriptions, treatments, etc.
    // would be implemented here following the same pattern
}
