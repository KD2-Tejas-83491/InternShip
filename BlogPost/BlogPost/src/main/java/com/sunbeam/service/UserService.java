package com.sunbeam.service;

import com.sunbeam.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByEmail(String email);
    User save(User user);
    Optional<User> findById(Long id);
    // New method
    User findByUsername(String username);
    
    List<User> findAll();
}
