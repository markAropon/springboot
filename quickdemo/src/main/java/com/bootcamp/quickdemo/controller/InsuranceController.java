package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.InsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.InsuranceResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping
    public ApiResponse<List<InsuranceResponseDTO>> getAllInsurances() {
        List<InsuranceResponseDTO> insurances = insuranceService.getAllInsurances();
        if (insurances.isEmpty()) {
            throw new ResourceNotFoundException("No insurances found.");
        }
        return DefaultResponse.displayFoundObject(insurances);
    }

    @GetMapping("/{id}")
    public ApiResponse<InsuranceResponseDTO> getInsuranceById(@PathVariable Long id) {
        Optional<InsuranceResponseDTO> insurance = Optional.ofNullable(insuranceService.getInsuranceById(id));
        return insurance.map(DefaultResponse::displayFoundObject)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance with ID " + id + " not found."));
    }

    @PostMapping
    public ApiResponse<InsuranceResponseDTO> createInsurance(@Valid @RequestBody InsuranceRequestDTO insuranceDto) {
        return DefaultResponse.displayCreatedObject(insuranceService.createInsurance(insuranceDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<InsuranceResponseDTO> updateInsurance(
            @PathVariable Long id,
            @Valid @RequestBody InsuranceRequestDTO insuranceDto
    ) {
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
