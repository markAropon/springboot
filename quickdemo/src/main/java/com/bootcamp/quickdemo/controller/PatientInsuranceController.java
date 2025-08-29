package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.dto.PatientInsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.PatientInsuranceResponseDTO;
import com.bootcamp.quickdemo.services.PatientInsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient-insurances")
@RequiredArgsConstructor
public class PatientInsuranceController {

    private final PatientInsuranceService patientInsuranceService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientInsuranceResponseDTO> getPatientInsuranceById(@PathVariable Long id) {
        return ResponseEntity.ok(patientInsuranceService.getPatientInsuranceById(id));
    }

    @PostMapping
    public ResponseEntity<PatientInsuranceResponseDTO> createPatientInsurance(@RequestBody PatientInsuranceRequestDTO dto) {
        PatientInsuranceResponseDTO created = patientInsuranceService.createPatientInsurance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientInsuranceResponseDTO> updatePatientInsurance(
            @PathVariable Long id, 
            @RequestBody PatientInsuranceRequestDTO dto) {
        PatientInsuranceResponseDTO updated = patientInsuranceService.updatePatientInsurance(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientInsurance(@PathVariable Long id) {
        patientInsuranceService.deletePatientInsurance(id);
        return ResponseEntity.noContent().build();
    }
}
