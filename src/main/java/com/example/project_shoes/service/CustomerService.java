package com.example.project_shoes.service;

import com.example.project_shoes.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();
    CustomerDTO findById(Long id);
    CustomerDTO save(CustomerDTO customerDTO);
    CustomerDTO update(CustomerDTO customerDTO);
    boolean delete(Long id);
    
    CustomerDTO findByUsername(String username);
    CustomerDTO findByEmail(String email);
    CustomerDTO findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
} 