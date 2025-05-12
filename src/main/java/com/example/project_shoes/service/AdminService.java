package com.example.project_shoes.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserDetailsService {
    boolean authenticate(String username, String password);
} 