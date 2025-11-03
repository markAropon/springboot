package com.bootcamp.quickdemo.controller;

import java.util.List;
import java.util.Optional;

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
import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.MedicalHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medical_history")
@RequiredArgsConstructor
@Tag(name = "Medical History", description = "[PATIENT, DOCTOR, ADMIN] Patient medical history management endpoints")
@RateLimit(limit = 3, durationSeconds = 15)
public class MedicalHistoryController {

    private final MedicalHistoryService service;

    @Operation(summary = "Get all medical history records", description = "Retrieves all medical history records in the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medical history records found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No medical history records found")
    })
    @GetMapping
    public ApiResponse<List<MedicalHistoryDTO>> getAll() {
        List<MedicalHistoryDTO> histories = service.getAll();
        if (histories.isEmpty()) {
            throw new ResourceNotFoundException("No medical history records found.");
        }
        return DefaultResponse.displayFoundObject(histories);
    }

    @Operation(summary = "Get medical history by ID", description = "Retrieve a specific medical history record by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medical history record found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Medical history record not found")
    })
    @GetMapping("/{id}")
    public ApiResponse<MedicalHistoryDTO> getById(
            @Parameter(description = "ID of the medical history record to retrieve", required = true, example = "1") @PathVariable Long id) {
        Optional<MedicalHistoryDTO> dto = Optional.ofNullable(service.getById(id));
        return dto.map(DefaultResponse::displayFoundObject)
                .orElseThrow(() -> new ResourceNotFoundException("Medical history with ID " + id + " not found."));
    }

    @Operation(summary = "Get medical history by patient ID", description = "Retrieve all medical history records for a specific patient")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medical history records found for patient"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No medical history records found for patient")
    })
    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<MedicalHistoryDTO>> getByPatient(
            @Parameter(description = "ID of the patient to retrieve medical history for", required = true, example = "1") @PathVariable Long patientId) {
        List<MedicalHistoryDTO> histories = service.getByPatient(patientId);
        if (histories.isEmpty()) {
            throw new ResourceNotFoundException("No medical history records found for patient ID " + patientId + ".");
        }
        return DefaultResponse.displayFoundObject(histories);
    }

    @Operation(summary = "Create medical history record", description = "Add a new medical history record to the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Medical history record created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ApiResponse<MedicalHistoryDTO> create(
            @Parameter(description = "Medical history details", required = true) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Medical history record that needs to be added", content = @Content(schema = @Schema(implementation = MedicalHistoryDTO.class))) @RequestBody MedicalHistoryDTO dto) {
        return DefaultResponse.displayCreatedObject(service.create(dto));
    }

    @Operation(summary = "Update medical history record", description = "Update an existing medical history record's information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medical history record updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Medical history record not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ApiResponse<MedicalHistoryDTO> update(
            @Parameter(description = "ID of the medical history record to update", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Updated medical history details", required = true) @RequestBody MedicalHistoryDTO dto) {
        MedicalHistoryDTO updated = service.update(id, dto);
        if (updated == null) {
            throw new ResourceNotFoundException("Cannot update medical history. ID " + id + " not found.");
        }
        return DefaultResponse.displayUpdatedObject(updated);
    }

    @Operation(summary = "Delete medical history record", description = "Remove a medical history record from the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medical history record deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Medical history record not found")
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @Parameter(description = "ID of the medical history record to delete", required = true, example = "1") @PathVariable Long id) {
        service.delete(id);
        return DefaultResponse.displayDeletedObject(null);
    }
}
