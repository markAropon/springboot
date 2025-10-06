package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.DoctorRequestDTO;
import com.bootcamp.quickdemo.dto.DoctorResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.services.DoctorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor Management", description = "[DOCTOR, ADMIN] Doctor management endpoints")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ApiResponse<List<DoctorResponseDTO>> getAllDoctors() {
        List<DoctorResponseDTO> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            throw new ResourceNotFoundException("No doctors found.");
        }
        return DefaultResponse.displayFoundObject(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {
        Optional<DoctorResponseDTO> doctor = Optional.ofNullable(doctorService.getDoctorById(id));
        return doctor.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found."));
    }

    @PostMapping
    public ApiResponse<DoctorResponseDTO> createDoctor(@Valid @RequestBody DoctorRequestDTO doctorDto) {
        return DefaultResponse.displayCreatedObject(doctorService.createDoctor(doctorDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<DoctorResponseDTO> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDTO doctorDto
    ) {
        DoctorResponseDTO updated = doctorService.updateDoctor(id, doctorDto);
        if (updated == null) {
            throw new ResourceNotFoundException("Cannot update doctor. ID " + id + " not found.");
        }
        return DefaultResponse.displayUpdatedObject(updated);
    }

  @DeleteMapping("/{id}") 
  public ApiResponse<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return DefaultResponse.displayDeletedObject(null);
    }
}
