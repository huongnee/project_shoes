package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingAndIsDeleteFalse(String name);
    
    @Query("SELECT p FROM Product p WHERE p.isDelete = false OR p.isDelete IS NULL")
    List<Product> findByIsDeleteFalse();
    
    @Query("SELECT p FROM Product p WHERE (p.isActive = true OR p.isActive IS NULL) AND (p.isDelete = false OR p.isDelete IS NULL)")
    List<Product> findByIsActiveTrueAndIsDeleteFalse();
    
    @Query("SELECT p FROM Product p")
    List<Product> findAllProducts();
    
    @Query(value = "SELECT * FROM product WHERE ISDELETE = 0 OR ISDELETE IS NULL", nativeQuery = true)
    List<Product> findAllNotDeleted();
    
    Product findByIdAndIsDeleteFalse(Long id);
    List<Product> findByIdCategoryAndIsDeleteFalse(Long idCategory);
} 