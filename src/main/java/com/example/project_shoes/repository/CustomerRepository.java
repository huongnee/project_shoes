package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainingAndIsDeleteFalse(String name);
    List<Customer> findByIsDeleteFalse();
    Customer findByIdAndIsDeleteFalse(Long id);
    Customer findByUsernameAndIsDeleteFalse(String username);
    Customer findByEmailAndIsDeleteFalse(String email);
} 