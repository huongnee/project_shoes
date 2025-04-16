package com.example.project_shoes.repository;

import com.example.project_shoes.entity.ProductConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductConfigRepository extends JpaRepository<ProductConfig, Long> {
    List<ProductConfig> findByIdProduct(Long idProduct);
    List<ProductConfig> findByIdConfig(Long idConfig);
    ProductConfig findByIdProductAndIdConfig(Long idProduct, Long idConfig);
} 