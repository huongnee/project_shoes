package com.example.project_shoes.repository;

import com.example.project_shoes.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByNameContainingAndIsDeleteFalse(String name);
    List<PaymentMethod> findByIsDeleteFalse();
    PaymentMethod findByIdAndIsDeleteFalse(Long id);
}