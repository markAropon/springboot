package com.bootcamp.quickdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.quickdemo.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
