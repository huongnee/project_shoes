package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.OrdersDetailsDTO;
import com.example.project_shoes.entity.OrdersDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersDetailsMapper {

    public OrdersDetailsDTO toDTO(OrdersDetails entity) {
        if (entity == null) {
            return null;
        }

        OrdersDetailsDTO dto = new OrdersDetailsDTO();
        dto.setId(entity.getId());
        dto.setIdOrder(entity.getIdOrder());
        if (entity.getOrder() != null) {
            dto.setOrderId(entity.getOrder().getIOrders());
        }
        dto.setIdProduct(entity.getIdProduct());
        if (entity.getProduct() != null) {
            dto.setProductName(entity.getProduct().getName());
        }
        dto.setPrice(entity.getPrice());
        dto.setQty(entity.getQty());
        dto.setReturnQty(entity.getReturnQty());
        dto.setTotal(entity.getTotal());

        return dto;
    }

    public OrdersDetails toEntity(OrdersDetailsDTO dto) {
        if (dto == null) {
            return null;
        }

        OrdersDetails entity = new OrdersDetails();
        entity.setId(dto.getId());
        entity.setIdOrder(dto.getIdOrder());
        entity.setIdProduct(dto.getIdProduct());
        entity.setPrice(dto.getPrice());
        entity.setQty(dto.getQty());
        entity.setReturnQty(dto.getReturnQty());
        entity.setTotal(dto.getTotal());

        return entity;
    }

    public List<OrdersDetailsDTO> toDTOList(List<OrdersDetails> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrdersDetails> toEntityList(List<OrdersDetailsDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 