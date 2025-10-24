package com.bootcamp.quickdemo.repository;

import com.bootcamp.quickdemo.model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {
    List<AppointmentModel> findByPatientId(Long patientId);

    List<AppointmentModel> findByDoctorId(Long doctorId);
}
