package com.sunbeam.dao;

import com.sunbeam.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);

    
}
