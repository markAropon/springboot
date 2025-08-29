package com.bootcamp.quickdemo.repository;

import com.bootcamp.quickdemo.model.VitalSignModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VitalSignRepository extends JpaRepository<VitalSignModel, Long> {
    List<VitalSignModel> findByAdmissionId(Integer admissionId);
}
