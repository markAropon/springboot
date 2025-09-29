package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.MedicalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medical_history")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final MedicalHistoryService service;

    @GetMapping
    public ApiResponse<List<MedicalHistoryDTO>> getAll() {
        List<MedicalHistoryDTO> histories = service.getAll();
        if (histories.isEmpty()) {
            throw new ResourceNotFoundException("No medical history records found.");
        }
        return DefaultResponse.displayFoundObject(histories);
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicalHistoryDTO> getById(@PathVariable Long id) {
        Optional<MedicalHistoryDTO> dto = Optional.ofNullable(service.getById(id));
        return dto.map(DefaultResponse::displayFoundObject)
                .orElseThrow(() -> new ResourceNotFoundException("Medical history with ID " + id + " not found."));
    }

    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<MedicalHistoryDTO>> getByPatient(@PathVariable Long patientId) {
        List<MedicalHistoryDTO> histories = service.getByPatient(patientId);
        if (histories.isEmpty()) {
            throw new ResourceNotFoundException("No medical history records found for patient ID " + patientId + ".");
        }
        return DefaultResponse.displayFoundObject(histories);
    }

    @PostMapping
    public ApiResponse<MedicalHistoryDTO> create(@RequestBody MedicalHistoryDTO dto) {
        return DefaultResponse.displayCreatedObject(service.create(dto));
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalHistoryDTO> update(@PathVariable Long id, @RequestBody MedicalHistoryDTO dto) {
        MedicalHistoryDTO updated = service.update(id, dto);
        if (updated == null) {
            throw new ResourceNotFoundException("Cannot update medical history. ID " + id + " not found.");
        }
        return DefaultResponse.displayUpdatedObject(updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return DefaultResponse.displayDeletedObject(null);
    }
}
