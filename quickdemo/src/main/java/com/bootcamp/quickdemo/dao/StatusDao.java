package com.bootcamp.quickdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.quickdemo.model.Status;

@Repository
public interface StatusDao extends JpaRepository<Status, Long> {
    Status findByStatusName(String statusName);
}
