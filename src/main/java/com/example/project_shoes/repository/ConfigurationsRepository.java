package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Configurations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationsRepository extends JpaRepository<Configurations, Long> {
    List<Configurations> findByNameContainingAndIsDeleteFalse(String name);
    List<Configurations> findByIsDeleteFalse();
    Configurations findByIdAndIsDeleteFalse(Long id);
} 