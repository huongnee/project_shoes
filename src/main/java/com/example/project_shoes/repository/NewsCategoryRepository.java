package com.example.project_shoes.repository;

import com.example.project_shoes.entity.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {
    List<NewsCategory> findByNameContainingAndIsDeleteFalse(String name);
    List<NewsCategory> findByIsDeleteFalse();
    NewsCategory findByIdAndIsDeleteFalse(Long id);
}