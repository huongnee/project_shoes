package com.example.project_shoes.repository;

import com.example.project_shoes.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
    List<ProductImages> findByIdProduct(Long idProduct);
    ProductImages findByIdProductAndName(Long idProduct, String name);
} 