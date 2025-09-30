package com.bootcamp.quickdemo.config.init;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bootcamp.quickdemo.model.Role;
import com.bootcamp.quickdemo.model.Users;
import com.bootcamp.quickdemo.repository.RoleRepository;
import com.bootcamp.quickdemo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserInitializer implements CommandLineRunner {
    
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {

        initRoles();
        initUsers();
    }
    
    private void initRoles() {
        log.info("Initializing roles...");
        if (!roleRepository.existsByRoleName("ROLE_ADMIN")) {
            Role adminRole = Role.builder()
                .roleName("ROLE_ADMIN")
                .description("Administrator role with full privileges")
                .build();
            roleRepository.save(adminRole);
            log.info("Created ROLE_ADMIN");
        }
        
        if (!roleRepository.existsByRoleName("ROLE_USER")) {
            Role userRole = Role.builder()
                .roleName("ROLE_USER")
                .description("Regular user role with limited privileges")
                .build();
            roleRepository.save(userRole);
            log.info("Created ROLE_USER");
        }
        
        if (!roleRepository.existsByRoleName("ROLE_DOCTOR")) {
            Role doctorRole = Role.builder()
                .roleName("ROLE_DOCTOR")
                .description("Doctor role with medical access privileges")
                .build();
            roleRepository.save(doctorRole);
            log.info("Created ROLE_DOCTOR");
        }
        
        if (!roleRepository.existsByRoleName("ROLE_PATIENT")) {
            Role patientRole = Role.builder()
                .roleName("ROLE_PATIENT")
                .description("Patient role with personal health record access")
                .build();
            roleRepository.save(patientRole);
            log.info("Created ROLE_PATIENT");
        }
    }
    
    private void initUsers() {
        log.info("Initializing users...");
        
        // Create admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            
            Users admin = Users.builder()
                .firstName("Admin")
                .lastName("User")
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@example.com")
                .isActive(true)
                .roles(adminRoles)
                .build();
            
            userRepository.save(admin);
            log.info("Created admin user");
        }
        
        // Create regular user if it doesn't exist
        if (!userRepository.existsByUsername("user")) {
            Role userRole = roleRepository.findByRoleName("ROLE_USER");
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            
            Users regularUser = Users.builder()
                .firstName("Regular")
                .lastName("User")
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .email("user@example.com")
                .isActive(true)
                .roles(userRoles)
                .build();
            
            userRepository.save(regularUser);
            log.info("Created regular user");
        }
        
        // Create doctor user if it doesn't exist
        if (!userRepository.existsByUsername("doctor")) {
            Role doctorRole = roleRepository.findByRoleName("ROLE_DOCTOR");
            Set<Role> doctorRoles = new HashSet<>();
            doctorRoles.add(doctorRole);
            
            Users doctor = Users.builder()
                .firstName("John")
                .lastName("Smith")
                .username("doctor")
                .password(passwordEncoder.encode("doctor123"))
                .email("doctor@example.com")
                .isActive(true)
                .roles(doctorRoles)
                .build();
            
            userRepository.save(doctor);
            log.info("Created doctor user");
        }
        
        // Create patient user if it doesn't exist
        if (!userRepository.existsByUsername("patient")) {
            Role patientRole = roleRepository.findByRoleName("ROLE_PATIENT");
            Set<Role> patientRoles = new HashSet<>();
            patientRoles.add(patientRole);
            
            Users patient = Users.builder()
                .firstName("Jane")
                .lastName("Doe")
                .username("patient")
                .password(passwordEncoder.encode("patient123"))
                .email("patient@example.com")
                .isActive(true)
                .roles(patientRoles)
                .build();
            
            userRepository.save(patient);
            log.info("Created patient user");
        }
    }
}
