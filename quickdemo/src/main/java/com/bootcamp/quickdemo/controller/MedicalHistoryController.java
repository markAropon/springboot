package com.bootcamp.quickdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;
import com.bootcamp.quickdemo.services.MedicalHistoryService;

import java.util.List;

@RestController
@RequestMapping("/medical_history")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final MedicalHistoryService service;

    @GetMapping
    public ResponseEntity<List<MedicalHistoryDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistoryDTO> getById(@PathVariable Long id) {
        MedicalHistoryDTO dto = service.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalHistoryDTO>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getByPatient(patientId));
    }

    @PostMapping
    public ResponseEntity<MedicalHistoryDTO> create(@RequestBody MedicalHistoryDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistoryDTO> update(@PathVariable Long id, @RequestBody MedicalHistoryDTO dto) {
        MedicalHistoryDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
