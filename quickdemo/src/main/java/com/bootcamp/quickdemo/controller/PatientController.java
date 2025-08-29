package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.dto.AdmissionResponseDTO;
import com.bootcamp.quickdemo.dto.InsuranceResponseDTO;
import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;
import com.bootcamp.quickdemo.dto.PatientInsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.PatientInsuranceResponseDTO;
import com.bootcamp.quickdemo.dto.PatientRequestDTO;
import com.bootcamp.quickdemo.dto.PatientResponseDTO;
import com.bootcamp.quickdemo.services.AdmissionService;
import com.bootcamp.quickdemo.services.InsuranceService;
import com.bootcamp.quickdemo.services.MedicalHistoryService;
import com.bootcamp.quickdemo.services.PatientInsuranceService;
import com.bootcamp.quickdemo.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AdmissionService admissionService;
    private final MedicalHistoryService medicalHistoryService;
    private final InsuranceService insuranceService;
    private final PatientInsuranceService patientInsuranceService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO patientDto) {
        PatientResponseDTO createdPatient = patientService.createPatient(patientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO patientDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    
    // Patient-related endpoints
    @GetMapping("/{id}/admissions")
    public ResponseEntity<List<AdmissionResponseDTO>> getPatientAdmissions(@PathVariable Long id) {
        return ResponseEntity.ok(admissionService.getAdmissionsByPatient(id));
    }
    
    @GetMapping("/{id}/medical-history")
    public ResponseEntity<List<MedicalHistoryDTO>> getPatientMedicalHistory(@PathVariable Long id) {
        return ResponseEntity.ok(medicalHistoryService.getByPatient(id));
    }
    
    @PostMapping("/{id}/medical-history")
    public ResponseEntity<MedicalHistoryDTO> addPatientMedicalHistory(
            @PathVariable Long id, 
            @RequestBody MedicalHistoryDTO dto) {
        dto.setPatientId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalHistoryService.create(dto));
    }
    
    @GetMapping("/{id}/insurances")
    public ResponseEntity<List<InsuranceResponseDTO>> getPatientInsurances(@PathVariable Long id) {
        return ResponseEntity.ok(insuranceService.getInsurancesByPatient(id));
    }
    
    @PostMapping("/{id}/insurances")
    public ResponseEntity<PatientInsuranceResponseDTO> addPatientInsurance(
            @PathVariable Long id,
            @RequestBody PatientInsuranceRequestDTO dto) {
        dto.setPatientId(id);
        PatientInsuranceResponseDTO created = patientInsuranceService.createPatientInsurance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
