package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.ProductDTO;
import com.example.project_shoes.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setContents(entity.getContents());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDescription(entity.getDescription());
        dto.setIdCategory(entity.getIdCategory());
        if (entity.getCategory() != null) {
            dto.setCategoryName(entity.getCategory().getName());
        }
        dto.setImage(entity.getImage());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
        dto.setMetaDescription(entity.getMetaDescription());
        dto.setMetaKeyword(entity.getMetaKeyword());
        dto.setMetaTitle(entity.getMetaTitle());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setSlug(entity.getSlug());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setContents(dto.getContents());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setDescription(dto.getDescription());
        entity.setIdCategory(dto.getIdCategory());
        entity.setImage(dto.getImage());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setMetaKeyword(dto.getMetaKeyword());
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setSlug(dto.getSlug());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());

        return entity;
    }

    public List<ProductDTO> toDTOList(List<Product> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Product> toEntityList(List<ProductDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 