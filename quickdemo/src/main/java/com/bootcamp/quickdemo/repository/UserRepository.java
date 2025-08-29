package com.bootcamp.quickdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.quickdemo.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
