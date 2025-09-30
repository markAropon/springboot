package com.bootcamp.quickdemo.config.init;

import java.time.LocalDateTime;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bootcamp.quickdemo.dao.RoleDao;
import com.bootcamp.quickdemo.model.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationRunner {
    private final RoleDao roleDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CreateRolesIfNotExists("USER", "Standard user role with limited permissions");
        CreateRolesIfNotExists("ADMIN", "Administrator role with full permissions");
    }

    public void CreateRolesIfNotExists(String roleName, String description) {
       if(roleDao.findByRoleName(roleName) == null) {
           Role role = new Role();
           role.setRoleName(roleName);
           role.setDescription(description);
           role.setDateCreated(LocalDateTime.now());
           role.setDateModified(LocalDateTime.now());
           roleDao.save(role);
           log.info("Default role '{}' created Successfully", roleName);
       } else {
           log.info("Default role '{}' already exists", roleName);
       }
    }
}

