package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.VitalSignRequestDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import java.util.List;

public interface VitalSignService {
    List<VitalSignResponseDTO> getAllVitalSigns();
    VitalSignResponseDTO getVitalSignById(Long id);
    VitalSignResponseDTO createVitalSign(VitalSignRequestDTO dto);
    List<VitalSignResponseDTO> getVitalSignsByAdmission(Integer admissionId);
}
