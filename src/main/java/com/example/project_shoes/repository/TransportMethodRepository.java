package com.example.project_shoes.repository;

import com.example.project_shoes.entity.TransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportMethodRepository extends JpaRepository<TransportMethod, Long> {
    List<TransportMethod> findByIsDeleteFalse();
    Optional<TransportMethod> findByIdAndIsDeleteFalse(Long id);
} 