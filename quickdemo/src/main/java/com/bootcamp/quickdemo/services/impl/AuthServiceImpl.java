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
    private final RefreshTokenService refreshTokenService;

    @Override
    public String userRegistration(UserRegistrationDTO userRegistrationDTO) {
        log.info("Registering new user: {}", userRegistrationDTO.getUsername());

        validateUser(userRegistrationDTO);

        Users user = new Users();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPhone(userRegistrationDTO.getPhone());
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
        log.info("Login attempt for user: {}", loginDTO.getUsernameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.generateToken(authentication);

        Users user = userDao.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        log.info("User '{}' logged in successfully.", user.getUsername());

        return new AuthResponseDTO("User logged in successfully", accessToken, refreshToken);
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

    @Override
    public AuthResponseDTO refreshAccessToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .map(token -> {
                    if (refreshTokenService.isExpired(token)) {
                        refreshTokenService.deleteByUser(token.getUser());
                        throw new IllegalArgumentException("Refresh token expired. Please log in again.");
                    }

                    String newAccessToken = tokenProvider.generateTokenFromUsername(token.getUser().getUsername());
                    return new AuthResponseDTO("Token refreshed successfully", newAccessToken, refreshToken);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
    }

    @Override
    public void logout(String username) {
        Users user = userDao.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        refreshTokenService.deleteByUser(user);
        log.info("User '{}' logged out successfully.", username);
    }

    @Override
    public String getAccessToken(String username) {
        return tokenProvider.generateTokenFromUsername(username);
    }
}
