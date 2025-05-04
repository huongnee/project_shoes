package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.ConfigurationsDTO;
import com.example.project_shoes.entity.Configurations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConfigurationsMapper {

    public ConfigurationsDTO toDTO(Configurations entity) {
        if (entity == null) {
            return null;
        }
        
        ConfigurationsDTO dto = new ConfigurationsDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setIsActive(entity.getIsActive() != null ? entity.getIsActive() : true);
        dto.setIsDelete(entity.getIsDelete() != null ? entity.getIsDelete() : false);
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        
        return dto;
    }
    
    public Configurations toEntity(ConfigurationsDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Configurations entity = new Configurations();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setIsDelete(dto.getIsDelete() != null ? dto.getIsDelete() : false);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        
        return entity;
    }
    
    public List<ConfigurationsDTO> toDTOList(List<Configurations> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<Configurations> toEntityList(List<ConfigurationsDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 