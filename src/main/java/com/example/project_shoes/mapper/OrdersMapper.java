package com.example.project_shoes.mapper;

import com.example.project_shoes.dto.OrdersDTO;
import com.example.project_shoes.entity.Orders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersMapper {

    public OrdersDTO toDTO(Orders entity) {
        if (entity == null) {
            return null;
        }

        OrdersDTO dto = new OrdersDTO();
        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setIOrders(entity.getIOrders());
        dto.setIdCustomer(entity.getIdCustomer());
        if (entity.getCustomer() != null) {
            dto.setCustomerName(entity.getCustomer().getName());
        }
        dto.setIdPayment(entity.getIdPayment());
        if (entity.getPaymentMethod() != null) {
            dto.setPaymentMethodName(entity.getPaymentMethod().getName());
        }
        dto.setIdTransport(entity.getIdTransport());
        if (entity.getTransportMethod() != null) {
            dto.setTransportMethodName(entity.getTransportMethod().getName());
        }
        dto.setIsActive(entity.getIsActive());
        dto.setIsDelete(entity.getIsDelete());
        dto.setNameReceiver(entity.getNameReceiver());
        dto.setNotes(entity.getNotes());
        dto.setOrdersDate(entity.getOrdersDate());
        dto.setPhone(entity.getPhone());
        dto.setTotalMoney(entity.getTotalMoney());

        return dto;
    }

    public Orders toEntity(OrdersDTO dto) {
        if (dto == null) {
            return null;
        }

        Orders entity = new Orders();
        entity.setId(dto.getId());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setIOrders(dto.getIOrders());
        entity.setIdCustomer(dto.getIdCustomer());
        entity.setIdPayment(dto.getIdPayment());
        entity.setIdTransport(dto.getIdTransport());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setNameReceiver(dto.getNameReceiver());
        entity.setNotes(dto.getNotes());
        entity.setOrdersDate(dto.getOrdersDate());
        entity.setPhone(dto.getPhone());
        entity.setTotalMoney(dto.getTotalMoney());

        return entity;
    }

    public List<OrdersDTO> toDTOList(List<Orders> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Orders> toEntityList(List<OrdersDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 