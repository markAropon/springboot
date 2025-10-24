// package com.bootcamp.quickdemo.mappers.Impl;

// import com.bootcamp.quickdemo.dto.AppointmentRequestDTO;
// import com.bootcamp.quickdemo.dto.AppointmentResponseDTO;
// import com.bootcamp.quickdemo.mappers.AppointmentMapper;
// import com.bootcamp.quickdemo.model.AppointmentModel;
// import org.springframework.stereotype.Component;

// @Component
// public class AppointmentMapperImpl implements AppointmentMapper {

// @Override
// public AppointmentModel toModel(AppointmentRequestDTO dto) {
// if (dto == null) {
// return null;
// }

// AppointmentModel model = new AppointmentModel();
// model.setPatientId(dto.getPatientId());
// model.setDoctorId(dto.getDoctorId());
// model.setAppointmentAt(dto.getAppointmentAt());
// model.setReason(dto.getReason());

// return model;
// }

// @Override
// public AppointmentResponseDTO toDto(AppointmentModel model) {
// if (model == null) {
// return null;
// }

// AppointmentResponseDTO dto = new AppointmentResponseDTO();
// dto.setId(model.getId());
// dto.setPatientId(model.getPatientId());
// dto.setDoctorId(model.getDoctorId());
// dto.setAppointmentAt(model.getAppointmentAt());
// dto.setReason(model.getReason());
// dto.setStatus(model.getStatus());

// return dto;
// }

// @Override
// public void updateModelFromDto(AppointmentRequestDTO dto, AppointmentModel
// model) {
// if (dto == null || model == null) {
// return;
// }

// // Update fields except ID
// model.setPatientId(dto.getPatientId());
// model.setDoctorId(dto.getDoctorId());
// model.setAppointmentAt(dto.getAppointmentAt());
// model.setReason(dto.getReason());
// }
// }