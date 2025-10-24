package com.bootcamp.quickdemo.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bootcamp.quickdemo.dto.AppointmentRequestDTO;
import com.bootcamp.quickdemo.dto.AppointmentResponseDTO;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.AppointmentMapper;
import com.bootcamp.quickdemo.model.AppointmentModel;
import com.bootcamp.quickdemo.repository.AppointmentRepository;
import com.bootcamp.quickdemo.services.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        AppointmentModel model = mapper.toModel(dto);
        model.setStatus("SCHEDULED");
        AppointmentModel saved = appointmentRepository.save(model);
        return mapper.toDto(saved);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {
        AppointmentModel model = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID " + id + " not found."));
        return mapper.toDto(model);
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto) {
        AppointmentModel model = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID " + id + " not found."));
        // update fields except id
        mapper.updateModelFromDto(dto, model);
        AppointmentModel saved = appointmentRepository.save(model);
        return mapper.toDto(saved);
    }
}
