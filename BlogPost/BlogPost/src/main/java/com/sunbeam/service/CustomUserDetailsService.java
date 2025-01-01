package com.sunbeam.service;

import com.sunbeam.dao.UserDao;
import com.sunbeam.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

//    @Autowired
//    private UserDao userDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userDao.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//
//        // Return the UserDetails object without authorities (roles)
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())  // email as username
//                .password(user.getPassword())  // encrypted password
//                .authorities(new ArrayList<>())  // No roles or authorities
//                .build();
//    }
//
//}
@Autowired
private UserService userService;

@Override
public UserDetails loadUserByUsername(String email) {
    User user = userService.findByEmail(email);
    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }

    List<GrantedAuthority> authorities = List.of(
        new SimpleGrantedAuthority("ROLE_" + user.getRoles().toString())
    );

    return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            authorities
    );
}}