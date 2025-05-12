package com.example.project_shoes.service.impl;

import com.example.project_shoes.service.AdminService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service("adminDetailsService")
public class AdminServiceImpl implements AdminService {

    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_USERNAME = "admin";
    private String encodedPassword;

    public AdminServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        this.encodedPassword = passwordEncoder.encode("admin123");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (ADMIN_USERNAME.equals(username)) {
            return User.builder()
                    .username(ADMIN_USERNAME)
                    .password(encodedPassword)
                    .roles("ADMIN")
                    .build();
        }
        throw new UsernameNotFoundException("Admin not found");
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (ADMIN_USERNAME.equals(username)) {
            return passwordEncoder.matches(password, encodedPassword);
        }
        return false;
    }
} 