package com.sunbeam.service;

import com.sunbeam.dao.UserDao;
import com.sunbeam.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
