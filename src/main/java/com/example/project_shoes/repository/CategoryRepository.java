package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContainingAndIsDeleteFalse(String name);
    List<Category> findByIsDeleteFalse();
    Category findByIdAndIsDeleteFalse(Long id);
}