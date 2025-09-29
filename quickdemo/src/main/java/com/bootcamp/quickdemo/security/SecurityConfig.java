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
    private final JWT_AuthenicationFIlter jwt_AuthenicationFIlter;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static final String[] PUBLIC_ENDPOINTS = {
        "/api/auth/register",
        "/api/auth/login",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**",
        "/error"
    };
    // Patients endpoints (requires Patients or ADMIN role)
    private static final String[] PATIENTS_ENDPOINTS = {

    };
    
    // Admin endpoints (requires ADMIN role)
    private static final String[] ADMIN_ENDPOINTS = {
        "/api/admin/**"
    };
     @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(PATIENTS_ENDPOINTS).hasAnyAuthority("ROLE_PATIENT", "ROLE_ADMIN")
                .requestMatchers(ADMIN_ENDPOINTS).hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwt_AuthenticationEntryPoint))
            .addFilterBefore(jwt_AuthenicationFIlter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    };
     @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
