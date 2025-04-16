package com.example.project_shoes.repository;

import com.example.project_shoes.entity.TransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportMethodRepository extends JpaRepository<TransportMethod, Long> {
    List<TransportMethod> findByNameContainingAndIsDeleteFalse(String name);
    List<TransportMethod> findByIsDeleteFalse();
    TransportMethod findByIdAndIsDeleteFalse(Long id);
} 