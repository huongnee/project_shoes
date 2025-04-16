package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.ProductImagesDTO;
import com.example.project_shoes.entity.ProductImages;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductImagesMapper {

    public ProductImagesDTO toDTO(ProductImages entity) {
        if (entity == null) {
            return null;
        }

        ProductImagesDTO dto = new ProductImagesDTO();
        dto.setId(entity.getId());
        dto.setIdProduct(entity.getIdProduct());
        if (entity.getProduct() != null) {
            dto.setProductName(entity.getProduct().getName());
        }
        dto.setName(entity.getName());
        dto.setUrlImg(entity.getUrlImg());

        return dto;
    }

    public ProductImages toEntity(ProductImagesDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductImages entity = new ProductImages();
        entity.setId(dto.getId());
        entity.setIdProduct(dto.getIdProduct());
        entity.setName(dto.getName());
        entity.setUrlImg(dto.getUrlImg());

        return entity;
    }

    public List<ProductImagesDTO> toDTOList(List<ProductImages> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductImages> toEntityList(List<ProductImagesDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}