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
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());

        return dto;
    }

    public Configurations toEntity(ConfigurationsDTO dto) {
        if (dto == null) {
            return null;
        }

        Configurations entity = new Configurations();
        entity.setId(dto.getId());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());

        return entity;
    }

    public List<ConfigurationsDTO> toDTOList(List<Configurations> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Configurations> toEntityList(List<ConfigurationsDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 