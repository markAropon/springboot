package com.bootcamp.quickdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.quickdemo.model.Role;



@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

Role findByRoleName(String roleName);
}