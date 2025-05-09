package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.ProductDTO;
import com.example.project_shoes.dto.ProductImagesDTO;
import com.example.project_shoes.entity.Product;
import com.example.project_shoes.entity.ProductImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @Autowired
    private ProductImagesMapper productImagesMapper;

    @Autowired
    private ProductConfigMapper productConfigMapper;

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
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
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

        // Map danh sách hình ảnh sản phẩm
        if (entity.getProductImages() != null) {
            List<ProductImagesDTO> productImagesDTOs = entity.getProductImages().stream()
                    .map(productImagesMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setProductImages(productImagesDTOs);
        }

        // Map product configs
        if (entity.getProductConfigs() != null) {
            dto.setProductConfigs(productConfigMapper.toDTOList(entity.getProductConfigs()));
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
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
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