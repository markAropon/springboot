package com.bootcamp.quickdemo.controller;

import com.bootcamp.quickdemo.common.ApiResponse;
import com.bootcamp.quickdemo.common.DefaultResponse;
import com.bootcamp.quickdemo.dto.AppointmentRequestDTO;
import com.bootcamp.quickdemo.dto.AppointmentResponseDTO;
import com.bootcamp.quickdemo.services.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment Management", description = "[PATIENT, ADMIN] Manage appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ApiResponse<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
        // Debug log the received DTO
        System.out.println("DEBUG - Received appointment request: " + dto);
        if (dto.getAppointmentAt() == null) {
            System.out.println("WARNING: appointmentAt is null!");
        }
        AppointmentResponseDTO created = appointmentService.createAppointment(dto);
        return DefaultResponse.displayCreatedObject(created);
    }

    @GetMapping
    public ApiResponse<List<AppointmentResponseDTO>> getAll() {
        List<AppointmentResponseDTO> list = appointmentService.getAllAppointments();
        if (list.isEmpty()) {
            // Return empty list instead of displayNoContent() to maintain type
            // compatibility
            return DefaultResponse.displayFoundObject(list);
        }
        return DefaultResponse.displayFoundObject(list);
    }

    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponseDTO> getById(@PathVariable Long id) {
        AppointmentResponseDTO dto = appointmentService.getAppointmentById(id);
        return DefaultResponse.displayFoundObject(dto);
    }

    @PutMapping("/{id}")
    public ApiResponse<AppointmentResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO updated = appointmentService.updateAppointment(id, dto);
        return DefaultResponse.displayUpdatedObject(updated);
    }
}
