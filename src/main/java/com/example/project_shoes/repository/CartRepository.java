package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomerIdAndIsDeleteFalse(Long customerId);
} 