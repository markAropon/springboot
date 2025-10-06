package com.bootcamp.quickdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bootcamp.quickdemo.security.jwt.JWT_AuthenicationFIlter;
import com.bootcamp.quickdemo.security.jwt.JWT_AuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWT_AuthenticationEntryPoint jwt_AuthenticationEntryPoint;
    private final JWT_AuthenicationFIlter jwtAuthenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static final String[] PUBLIC_ENDPOINTS = {
            // Auth endpoints
            "/api/auth/register",
            "/api/auth/login",

            "/",
            "/colors",
            "/test-error",
            "/validate/**",

            // Swagger/OpenAPI documentation
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",

            // System endpoints
            "/error"
    };

    // Patient endpoints (requires ROLE_PATIENT or ROLE_DOCTOR or ROLE_ADMIN)
    private static final String[] PATIENTS_ENDPOINTS = {
            "/patients/**",
            "/medical-history/**",
            "/vital-signs/**",
            "/patient-insurances/**"
    };

    // Doctor endpoints (requires ROLE_DOCTOR or ROLE_ADMIN)
    private static final String[] DOCTOR_ENDPOINTS = {
            "/doctors/**",
            "/admissions/**"
    };

    // Admin endpoints (requires ROLE_ADMIN)
    private static final String[] ADMIN_ENDPOINTS = {
            "/api/admin/**",
            "/users/**",
            "/insurances/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints accessible without authentication
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                        // Patient-related endpoints - accessible by patients, doctors, and admins
                        .requestMatchers(PATIENTS_ENDPOINTS)
                        .hasAnyAuthority("ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_ADMIN")

                        // Doctor-related endpoints - accessible by doctors and admins
                        .requestMatchers(DOCTOR_ENDPOINTS).hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")

                        // Admin-only endpoints
                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority("ROLE_ADMIN")

                        // Any other endpoints require authentication
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwt_AuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
