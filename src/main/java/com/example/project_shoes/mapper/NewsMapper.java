package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.NewsDTO;
import com.example.project_shoes.entity.News;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsMapper {

    public NewsDTO toDTO(News entity) {
        if (entity == null) {
            return null;
        }

        NewsDTO dto = new NewsDTO();
        dto.setId(entity.getId());
        dto.setContents(entity.getContents());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDescription(entity.getDescription());
        dto.setIdNewsCategory(entity.getIdNewsCategory());
        if (entity.getNewsCategory() != null) {
            dto.setNewsCategoryName(entity.getNewsCategory().getName());
        }
        dto.setImage(entity.getImage());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
        dto.setMetaDescription(entity.getMetaDescription());
        dto.setMetaKeyword(entity.getMetaKeyword());
        dto.setMetaTitle(entity.getMetaTitle());
        dto.setName(entity.getName());
        dto.setSlug(entity.getSlug());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }

    public News toEntity(NewsDTO dto) {
        if (dto == null) {
            return null;
        }

        News entity = new News();
        entity.setId(dto.getId());
        entity.setContents(dto.getContents());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setDescription(dto.getDescription());
        entity.setIdNewsCategory(dto.getIdNewsCategory());
        entity.setImage(dto.getImage());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setMetaKeyword(dto.getMetaKeyword());
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setName(dto.getName());
        entity.setSlug(dto.getSlug());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());

        return entity;
    }

    public List<NewsDTO> toDTOList(List<News> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<News> toEntityList(List<NewsDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    public void updateEntityFromDTO(NewsDTO dto, News entity) {
        if (dto == null || entity == null) return;
        
        entity.setName(dto.getName());
        entity.setContents(dto.getContents());
        entity.setDescription(dto.getDescription());
        entity.setIdNewsCategory(dto.getIdNewsCategory());
        entity.setImage(dto.getImage());
        entity.setIsActive(dto.getIsActive());
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setMetaKeyword(dto.getMetaKeyword());
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setSlug(dto.getSlug());
        // Các trường createdDate/createdBy không cập nhật khi edit[2]
    }
    
} 