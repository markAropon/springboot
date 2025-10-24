package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.AppointmentRequestDTO;
import com.bootcamp.quickdemo.dto.AppointmentResponseDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto);

    List<AppointmentResponseDTO> getAllAppointments();

    AppointmentResponseDTO getAppointmentById(Long id);

    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto);
}
