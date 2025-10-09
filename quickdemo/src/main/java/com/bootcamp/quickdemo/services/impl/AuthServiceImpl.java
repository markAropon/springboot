package com.bootcamp.quickdemo.services.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bootcamp.quickdemo.dao.RoleDao;
import com.bootcamp.quickdemo.dao.UserDao;
import com.bootcamp.quickdemo.dto.AuthResponseDTO;
import com.bootcamp.quickdemo.dto.LoginDTO;
import com.bootcamp.quickdemo.dto.UserDetailsDTO;
import com.bootcamp.quickdemo.dto.UserRegistrationDTO;
import com.bootcamp.quickdemo.model.Role;
import com.bootcamp.quickdemo.model.Users;
import com.bootcamp.quickdemo.security.jwt.JWT_TokenProvider;
import com.bootcamp.quickdemo.services.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWT_TokenProvider tokenProvider;

    @Override
    public String userRegistration(UserRegistrationDTO userRegistrationDTO) {
        log.info("Registering new user: {}", userRegistrationDTO.getUsername());

        validateUser(userRegistrationDTO);

        Users user = new Users();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setActive(true);

        // Use the role provided in the registration request instead of hardcoding
        String requestedRoleName = "ROLE_" + userRegistrationDTO.getRole().toUpperCase();
        Role requestedRole = roleDao.findByRoleName(requestedRoleName);
        if (requestedRole == null) {
            log.error("Requested role '{}' not found in the database", requestedRoleName);
            throw new IllegalArgumentException(
                    "Role not found. Please ensure '" + requestedRoleName + "' role exists in database");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(requestedRole);
        user.setRoles(roles);
        userDao.save(user);

        log.info("User {} registered successfully with {} role", user.getUsername(), requestedRoleName);

        return "User registered successfully";
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        System.out.println("DEBUG: Login attempt for user: " + loginDTO.getUsernameOrEmail());
        System.out.println("DEBUG: Password provided (length): " +
                (loginDTO.getPassword() != null ? loginDTO.getPassword().length() : "null"));

        try {
            Users user = userDao.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail())
                    .orElse(null);

            if (user != null) {
                System.out.println("DEBUG: User found in database: " + user.getUsername());
                System.out.println("DEBUG: User password hash: " + user.getPassword());
                System.out.println("DEBUG: Attempting to match provided password...");

                boolean matches = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
                System.out.println("DEBUG: Password matches: " + matches);
            } else {
                System.out.println("DEBUG: ⚠️ User not found in database!");
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Error during manual verification: " + e.getMessage());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsernameOrEmail(),
                            loginDTO.getPassword()));

            System.out.println("DEBUG: Authentication successful!");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);
            System.out.println("DEBUG: Token generated successfully");

            return new AuthResponseDTO("User logged in successfully", token);
        } catch (Exception e) {
            System.out.println("DEBUG: Authentication failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public UserDetailsDTO getCurrentUser(String username) {
        log.info("Retrieving user details for: {}", username);

        Users user = userDao.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        return UserDetailsDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .isActive(user.isActive())
                .dateCreated(user.getDateCreated())
                .dateModified(user.getDateModified())
                .build();
    }

    private void validateUser(UserRegistrationDTO userRegistrationDTO) {
        if (userDao.existsByUsername(userRegistrationDTO.getUsername())) {
            log.warn("Username '{}' is already taken", userRegistrationDTO.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }

        if (userDao.existsByEmail(userRegistrationDTO.getEmail())) {
            log.warn("Email '{}' is already taken", userRegistrationDTO.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }
    }
}
