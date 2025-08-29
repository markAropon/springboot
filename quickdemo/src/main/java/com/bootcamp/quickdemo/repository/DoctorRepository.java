package com.bootcamp.quickdemo.repository;

import com.bootcamp.quickdemo.model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel, Long> {
}   
