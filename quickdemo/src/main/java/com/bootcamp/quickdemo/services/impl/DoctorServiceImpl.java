package com.bootcamp.quickdemo.services.impl;

import com.bootcamp.quickdemo.dto.DoctorRequestDTO;
import com.bootcamp.quickdemo.dto.DoctorResponseDTO;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.Impl.DoctorMapper;
import com.bootcamp.quickdemo.model.DoctorModel;
import com.bootcamp.quickdemo.repository.DoctorRepository;
import com.bootcamp.quickdemo.services.DoctorService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        DoctorModel doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO doctorDto) {
        validateDoctorRequest(doctorDto);
        
        DoctorModel doctor = doctorMapper.requestDtoToEntity(doctorDto);
        DoctorModel savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(savedDoctor);
    }

    @Override
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO doctorDto) {
        validateDoctorRequest(doctorDto);
        
        DoctorModel existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
        
        DoctorModel updatedDoctor = doctorMapper.requestDtoToEntity(doctorDto, existingDoctor);
        DoctorModel savedDoctor = doctorRepository.save(updatedDoctor);
        return doctorMapper.toDto(savedDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor with ID " + id + " not found");
        }
        doctorRepository.deleteById(id);
    }

    private void validateDoctorRequest(DoctorRequestDTO doctor) {
        if (doctor.getName() == null || doctor.getName().isEmpty()) {
            throw new BadRequestException("Doctor name cannot be empty");
        }
        if (doctor.getSpecialty() == null || doctor.getSpecialty().isEmpty()) {
            throw new BadRequestException("Doctor specialty cannot be empty");
        }
    }
}
