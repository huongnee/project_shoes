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
        dto.setName(entity.getName());
        dto.setContents(entity.getContents());
        dto.setDescription(entity.getDescription());
        dto.setIdCategory(entity.getIdCategory());
        dto.setImage(entity.getImage());
        dto.setIsActive(entity.getIsActive() != null ? entity.getIsActive() : true);
        dto.setIsDelete(entity.getIsDelete() != null ? entity.getIsDelete() : false);
        dto.setMetaDescription(entity.getMetaDescription());
        dto.setMetaKeyword(entity.getMetaKeyword());
        dto.setMetaTitle(entity.getMetaTitle());
        dto.setNotes(entity.getNotes());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setSlug(entity.getSlug());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        
        // Thêm tên danh mục nếu có thông tin danh mục
        if (entity.getCategory() != null) {
            dto.setCategoryName(entity.getCategory().getName());
        }
        
        return dto;
    }
    
    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setContents(dto.getContents());
        entity.setDescription(dto.getDescription());
        entity.setIdCategory(dto.getIdCategory());
        entity.setImage(dto.getImage());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setIsDelete(dto.getIsDelete() != null ? dto.getIsDelete() : false);
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setMetaKeyword(dto.getMetaKeyword());
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setNotes(dto.getNotes());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setSlug(dto.getSlug());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        
        return entity;
    }
    
    public List<ProductDTO> toDTOList(List<Product> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<Product> toEntityList(List<ProductDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 