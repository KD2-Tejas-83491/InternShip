package com.sunbeam.controller;

import com.sunbeam.dto.LoginDTO;
import com.sunbeam.dto.UserDTO;
import com.sunbeam.entities.Role;
import com.sunbeam.entities.User;
import com.sunbeam.security.JwtHelper;
import com.sunbeam.service.CustomUserDetailsService;
import com.sunbeam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collections;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            if (userService.findByEmail(userDTO.getEmail()) != null) {
                return ResponseEntity.badRequest().body("Email is already in use");
            }

            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            // Convert role from String to Role enum, defaulting to USER if not provided
            Role role = userDTO.getRoles() != null 
                        ? Role.valueOf(userDTO.getRoles().toUpperCase()) 
                        : Role.USER; // Default to USER
            user.setRoles(role);

            Timestamp now = new Timestamp(System.currentTimeMillis());
            user.setCreatedAt(now);
            user.setUpdatedAt(now);

            userService.save(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (IllegalArgumentException e) {
            // Handle case where the role provided is invalid
            return ResponseEntity.badRequest().body("Invalid role: " + userDTO.getRoles());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration: " + e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDTO.getEmail());
            String token = jwtHelper.generateToken(userDetails);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching users: " + e.getMessage());
        }
    }
}
