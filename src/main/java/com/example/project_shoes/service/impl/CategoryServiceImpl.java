package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.CategoryDTO;
import com.example.project_shoes.entity.Category;
import com.example.project_shoes.mapper.CategoryMapper;
import com.example.project_shoes.repository.CategoryRepository;
import com.example.project_shoes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findByIsDeleteFalse();
        return categoryMapper.toDTOList(categories);
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findByIdAndIsDeleteFalse(id);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        category.setIsDelete(false);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findByIdAndIsDeleteFalse(categoryDTO.getId());
        if (existingCategory != null) {
            Category category = categoryMapper.toEntity(categoryDTO);
            category.setIsDelete(false);
            Category updatedCategory = categoryRepository.save(category);
            return categoryMapper.toDTO(updatedCategory);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Category existingCategory = categoryRepository.findByIdAndIsDeleteFalse(id);
        if (existingCategory != null) {
            existingCategory.setIsDelete(true);
            categoryRepository.save(existingCategory);
            return true;
        }
        return false;
    }

    @Override
    public List<CategoryDTO> findByName(String name) {
        List<Category> categories = categoryRepository.findByNameContainingAndIsDeleteFalse(name);
        return categoryMapper.toDTOList(categories);
    }
} 