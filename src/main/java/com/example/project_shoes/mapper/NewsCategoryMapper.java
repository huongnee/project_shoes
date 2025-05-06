package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.NewsCategoryDTO;
import com.example.project_shoes.entity.NewsCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsCategoryMapper {

    public NewsCategoryDTO toDTO(NewsCategory entity) {
        if (entity == null) {
            return null;
        }

        NewsCategoryDTO dto = new NewsCategoryDTO();
        dto.setId(entity.getId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setIcon(entity.getIcon());
        dto.setIdParent(entity.getIdParent());
        // dto.setIsActive(entity.getIsActive());
        dto.setIsActive(entity.getIsActive() != null ? entity.getIsActive() : false);

        dto.setIsDelete(entity.getIsDelete());
        dto.setMetaDescription(entity.getMetaDescription());
        dto.setMetaKeyword(entity.getMetaKeyword());
        dto.setMetaTitle(entity.getMetaTitle());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setSlug(entity.getSlug());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }

    public NewsCategory toEntity(NewsCategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        NewsCategory entity = new NewsCategory();
        entity.setId(dto.getId());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setIcon(dto.getIcon());
        entity.setIdParent(dto.getIdParent());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setMetaKeyword(dto.getMetaKeyword());
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setSlug(dto.getSlug());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());

        return entity;
    }

    public List<NewsCategoryDTO> toDTOList(List<NewsCategory> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<NewsCategory> toEntityList(List<NewsCategoryDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    public void updateEntityFromDTO(NewsCategoryDTO dto, NewsCategory entity) {
        entity.setName(dto.getName());
        entity.setIsActive(dto.getIsActive());
        entity.setIcon(dto.getIcon());
        entity.setIdParent(dto.getIdParent());
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setMetaKeyword(dto.getMetaKeyword());
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setNotes(dto.getNotes());
        entity.setSlug(dto.getSlug());
    }
    
} 