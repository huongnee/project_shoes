package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.PaymentMethodDTO;
import com.example.project_shoes.entity.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMethodMapper {

    public PaymentMethodDTO toDTO(PaymentMethod entity) {
        if (entity == null) {
            return null;
        }

        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }

    public PaymentMethod toEntity(PaymentMethodDTO dto) {
        if (dto == null) {
            return null;
        }

        PaymentMethod entity = new PaymentMethod();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setUpdatedDate(dto.getUpdatedDate());

        return entity;
    }

    public List<PaymentMethodDTO> toDTOList(List<PaymentMethod> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentMethod> toEntityList(List<PaymentMethodDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}