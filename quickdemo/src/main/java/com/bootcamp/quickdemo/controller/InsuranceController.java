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
import com.bootcamp.quickdemo.dto.InsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.InsuranceResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.InsuranceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
@Tag(name = "Insurance Management", description = "[ADMIN] Insurance provider management endpoints")
@RateLimit(limit = 3, durationSeconds = 15)
public class InsuranceController {

    private final InsuranceService insuranceService;

    @Operation(summary = "Get all insurance providers", description = "Retrieve a list of all insurance providers in the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insurance providers found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No insurance providers found")
    })
    @GetMapping
    public ApiResponse<List<InsuranceResponseDTO>> getAllInsurances() {
        List<InsuranceResponseDTO> insurances = insuranceService.getAllInsurances();
        if (insurances.isEmpty()) {
            throw new ResourceNotFoundException("No insurances found.");
        }
        return DefaultResponse.displayFoundObject(insurances);
    }

    @Operation(summary = "Get insurance provider by ID", description = "Retrieve a specific insurance provider by their ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insurance provider found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Insurance provider not found")
    })
    @GetMapping("/{id}")
    public ApiResponse<InsuranceResponseDTO> getInsuranceById(
            @Parameter(description = "ID of the insurance provider to retrieve", required = true, example = "1") @PathVariable Long id) {
        Optional<InsuranceResponseDTO> insurance = Optional.ofNullable(insuranceService.getInsuranceById(id));
        return insurance.map(DefaultResponse::displayFoundObject)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance with ID " + id + " not found."));
    }

    @Operation(summary = "Create a new insurance provider", description = "Add a new insurance provider to the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Insurance provider created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ApiResponse<InsuranceResponseDTO> createInsurance(
            @Parameter(description = "Insurance provider details", required = true) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Insurance provider object that needs to be added", content = @Content(schema = @Schema(implementation = InsuranceRequestDTO.class))) @Valid @RequestBody InsuranceRequestDTO insuranceDto) {
        return DefaultResponse.displayCreatedObject(insuranceService.createInsurance(insuranceDto));
    }

    @Operation(summary = "Update insurance provider", description = "Update an existing insurance provider's information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insurance provider updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Insurance provider not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ApiResponse<InsuranceResponseDTO> updateInsurance(
            @Parameter(description = "ID of the insurance provider to update", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Updated insurance provider details", required = true) @Valid @RequestBody InsuranceRequestDTO insuranceDto) {
        InsuranceResponseDTO updated = insuranceService.updateInsurance(id, insuranceDto);
        if (updated == null) {
            throw new ResourceNotFoundException("Cannot update insurance. ID " + id + " not found.");
        }
        return DefaultResponse.displayUpdatedObject(updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return DefaultResponse.displayDeletedObject(null);
    }
}
