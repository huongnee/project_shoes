package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.CustomerDTO;
import com.example.project_shoes.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer entity) {
        if (entity == null) {
            return null;
        }

        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setAvatar(entity.getAvatar());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setEmail(entity.getEmail());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        dto.setPhone(entity.getPhone());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setUsername(entity.getUsername());

        return dto;
    }

    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        Customer entity = new Customer();
        entity.setId(dto.getId());
        entity.setAddress(dto.getAddress());
        entity.setAvatar(dto.getAvatar());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setEmail(dto.getEmail());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        entity.setUsername(dto.getUsername());

        return entity;
    }

    public List<CustomerDTO> toDTOList(List<Customer> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Customer> toEntityList(List<CustomerDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 