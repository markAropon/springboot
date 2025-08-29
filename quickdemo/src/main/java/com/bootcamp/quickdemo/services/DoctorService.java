package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.DoctorRequestDTO;
import com.bootcamp.quickdemo.dto.DoctorResponseDTO;
import java.util.List;

public interface DoctorService {
    List<DoctorResponseDTO> getAllDoctors();
    DoctorResponseDTO getDoctorById(Long id);
    DoctorResponseDTO createDoctor(DoctorRequestDTO doctorDto);
    DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO doctorDto);
    void deleteDoctor(Long id);
}
