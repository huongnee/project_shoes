package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.CategoryDTO;
import com.example.project_shoes.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category entity) {
        if (entity == null) {
            return null;
        }
        
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIcon(entity.getIcon());
        dto.setIdParent(entity.getIdParent());
        dto.setSlug(entity.getSlug());
        dto.setNotes(entity.getNotes());
        dto.setIsActive(entity.getIsActive() != null ? entity.getIsActive() : true);
        dto.setIsDelete(entity.getIsDelete() != null ? entity.getIsDelete() : false);
        dto.setMetaTitle(entity.getMetaTitle());
        dto.setMetaKeyword(entity.getMetaKeyword());
        dto.setMetaDescription(entity.getMetaDescription());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        
        return dto;
    }
    
    public Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setIcon(dto.getIcon());
        entity.setIdParent(dto.getIdParent());
        entity.setSlug(dto.getSlug());
        entity.setNotes(dto.getNotes());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setIsDelete(dto.getIsDelete() != null ? dto.getIsDelete() : false);
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setMetaKeyword(dto.getMetaKeyword());
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        
        return entity;
    }
    
    public List<CategoryDTO> toDTOList(List<Category> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<Category> toEntityList(List<CategoryDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 