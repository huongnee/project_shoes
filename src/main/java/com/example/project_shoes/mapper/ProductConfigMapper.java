package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.ProductConfigDTO;
import com.example.project_shoes.entity.ProductConfig;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConfigMapper {

    public ProductConfigDTO toDTO(ProductConfig entity) {
        if (entity == null) {
            return null;
        }

        ProductConfigDTO dto = new ProductConfigDTO();
        dto.setId(entity.getId());
        dto.setIdConfig(entity.getIdConfig());
        if (entity.getConfiguration() != null) {
            dto.setConfigName(entity.getConfiguration().getName());
        }
        dto.setIdProduct(entity.getIdProduct());
        if (entity.getProduct() != null) {
            dto.setProductName(entity.getProduct().getName());
        }
        dto.setValue(entity.getValue());

        return dto;
    }

    public ProductConfig toEntity(ProductConfigDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductConfig entity = new ProductConfig();
        entity.setId(dto.getId());
        entity.setIdConfig(dto.getIdConfig());
        entity.setIdProduct(dto.getIdProduct());
        entity.setValue(dto.getValue());

        return entity;
    }

    public List<ProductConfigDTO> toDTOList(List<ProductConfig> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductConfig> toEntityList(List<ProductConfigDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 