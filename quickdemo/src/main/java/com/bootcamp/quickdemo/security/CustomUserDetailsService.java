package com.bootcamp.quickdemo.security;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bootcamp.quickdemo.dao.UserDao;
import com.bootcamp.quickdemo.model.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao user_dao;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("DEBUG: Attempting to load user: " + usernameOrEmail);
        
        try {
            final Users user = user_dao.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
            
            System.out.println("DEBUG: Found user: " + user.getUsername() + ", email: " + user.getEmail());
            System.out.println("DEBUG: Password hash: " + user.getPassword());
            
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                System.out.println("DEBUG: ⚠️ User has NO ROLES assigned!");
            } else {
                System.out.println("DEBUG: User has " + user.getRoles().size() + " roles:");
                user.getRoles().forEach(role -> System.out.println("DEBUG: Role: " + role.getRoleName()));
            }
            
            Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> {
                    String authority = "ROLE_" + role.getRoleName().toUpperCase();
                    System.out.println("DEBUG: Granting authority: " + authority);
                    return new SimpleGrantedAuthority(authority);
                })
                .collect(Collectors.toSet());
            
            if (authorities.isEmpty()) {
                System.out.println("DEBUG: ⚠️ User has NO AUTHORITIES!");
            }
                
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(), 
                user.getPassword(),
                authorities);
                
        } catch (Exception e) {
            System.out.println("DEBUG: Error loading user: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }   
}
