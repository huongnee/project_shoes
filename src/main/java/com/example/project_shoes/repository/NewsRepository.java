package com.example.project_shoes.repository;

import com.example.project_shoes.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByNameContainingAndIsDeleteFalse(String name);
    List<News> findByIsDeleteFalse();
    News findByIdAndIsDeleteFalse(Long id);
    List<News> findByIdNewsCategoryAndIsDeleteFalse(Long idNewsCategory);
} 