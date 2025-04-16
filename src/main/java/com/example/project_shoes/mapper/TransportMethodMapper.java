package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.TransportMethodDTO;
import com.example.project_shoes.entity.TransportMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransportMethodMapper {

    public TransportMethodDTO toDTO(TransportMethod entity) {
        if (entity == null) {
            return null;
        }

        TransportMethodDTO dto = new TransportMethodDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }

    public TransportMethod toEntity(TransportMethodDTO dto) {
        if (dto == null) {
            return null;
        }

        TransportMethod entity = new TransportMethod();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setUpdatedDate(dto.getUpdatedDate());

        return entity;
    }

    public List<TransportMethodDTO> toDTOList(List<TransportMethod> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransportMethod> toEntityList(List<TransportMethodDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 