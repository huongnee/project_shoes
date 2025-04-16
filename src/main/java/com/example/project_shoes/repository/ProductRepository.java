package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingAndIsDeleteFalse(String name);
    List<Product> findByIsDeleteFalse();
    Product findByIdAndIsDeleteFalse(Long id);
    List<Product> findByIdCategoryAndIsDeleteFalse(Long idCategory);
} 