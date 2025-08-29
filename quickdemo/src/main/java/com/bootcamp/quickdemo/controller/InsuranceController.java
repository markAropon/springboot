package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.dto.InsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.InsuranceResponseDTO;
import com.bootcamp.quickdemo.services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping
    public ResponseEntity<List<InsuranceResponseDTO>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.getAllInsurances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceResponseDTO> getInsuranceById(@PathVariable Long id) {
        return ResponseEntity.ok(insuranceService.getInsuranceById(id));
    }

    @PostMapping
    public ResponseEntity<InsuranceResponseDTO> createInsurance(@RequestBody InsuranceRequestDTO insuranceDto) {
        InsuranceResponseDTO createdInsurance = insuranceService.createInsurance(insuranceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInsurance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceResponseDTO> updateInsurance(@PathVariable Long id, @RequestBody InsuranceRequestDTO insuranceDto) {
        return ResponseEntity.ok(insuranceService.updateInsurance(id, insuranceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }
}
