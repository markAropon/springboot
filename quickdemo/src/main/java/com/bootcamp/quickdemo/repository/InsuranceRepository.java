package com.bootcamp.quickdemo.repository;

import com.bootcamp.quickdemo.model.InsuranceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<InsuranceModel, Long> {
}
