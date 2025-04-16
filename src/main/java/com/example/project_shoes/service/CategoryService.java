package com.example.project_shoes.service;

import com.example.project_shoes.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
    CategoryDTO findById(Long id);
    CategoryDTO save(CategoryDTO categoryDTO);
    CategoryDTO update(CategoryDTO categoryDTO);
    boolean delete(Long id);
    List<CategoryDTO> findByName(String name);
} 