package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.*;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ApiResponse<List<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age
    ) {
        List<PatientResponseDTO> patients = (List<PatientResponseDTO>) patientService.getPatients(page, size, name, age);
        return DefaultResponse.displayFoundObject(patients);
    }

    @GetMapping("/{id}")
    public ApiResponse<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        Optional<PatientResponseDTO> patient = Optional.ofNullable(patientService.getPatientById(id));
        return patient.map(DefaultResponse::displayFoundObject)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + id + " not found."));
    }

    @PostMapping
    public ApiResponse<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientDto) {
        return DefaultResponse.displayCreatedObject(patientService.createPatient(patientDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<PatientResponseDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientDto) {
        PatientResponseDTO updated = patientService.updatePatient(id, patientDto);
        if (updated == null) {
            throw new ResourceNotFoundException("Cannot update patient. ID " + id + " not found.");
        }
        return DefaultResponse.displayUpdatedObject(updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return DefaultResponse.displayDeletedObject(null);
    }

    @GetMapping("/{id}/admissions")
    public ApiResponse<List<AdmissionResponseDTO>> getPatientAdmissions(@PathVariable Long id) {
        List<AdmissionResponseDTO> admissions = admissionService.getAdmissionsByPatient(id);
        if (admissions.isEmpty()) {
            throw new ResourceNotFoundException("No admissions found for patient ID " + id + ".");
        }
        return DefaultResponse.displayFoundObject(admissions);
    }

    @GetMapping("/{id}/medical-history")
    public ApiResponse<List<MedicalHistoryDTO>> getPatientMedicalHistory(@PathVariable Long id) {
        List<MedicalHistoryDTO> histories = medicalHistoryService.getByPatient(id);
        if (histories.isEmpty()) {
            throw new ResourceNotFoundException("No medical history found for patient ID " + id + ".");
        }
        return DefaultResponse.displayFoundObject(histories);
    }

    @PostMapping("/{id}/medical-history")
    public ApiResponse<MedicalHistoryDTO> addPatientMedicalHistory(
            @PathVariable Long id,
            @RequestBody MedicalHistoryDTO dto
    ) {
        dto.setPatientId(id);
        return DefaultResponse.displayCreatedObject(medicalHistoryService.create(dto));
    }

    @GetMapping("/{id}/insurances")
    public ApiResponse<List<InsuranceResponseDTO>> getPatientInsurances(@PathVariable Long id) {
        List<InsuranceResponseDTO> insurances = insuranceService.getInsurancesByPatient(id);
        if (insurances.isEmpty()) {
            throw new ResourceNotFoundException("No insurances found for patient ID " + id + ".");
        }
        return DefaultResponse.displayFoundObject(insurances);
    }

    @PostMapping("/{id}/insurances")
    public ApiResponse<PatientInsuranceResponseDTO> addPatientInsurance(
            @PathVariable Long id,
            @RequestBody PatientInsuranceRequestDTO dto
    ) {
        dto.setPatientId(id);
        return DefaultResponse.displayCreatedObject(patientInsuranceService.createPatientInsurance(dto));
    }
}
