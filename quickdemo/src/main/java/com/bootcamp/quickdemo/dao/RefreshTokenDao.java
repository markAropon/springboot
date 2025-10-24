package com.bootcamp.quickdemo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.quickdemo.model.RefreshToken;
import com.bootcamp.quickdemo.model.Users;

public interface RefreshTokenDao extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(Users user);
}
