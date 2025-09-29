package com.bootcamp.quickdemo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.quickdemo.model.Users;


@Repository
public interface UserDao extends JpaRepository<Users, Long> {

    Optional<Users> findByUsernameOrEmail(String username, String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
