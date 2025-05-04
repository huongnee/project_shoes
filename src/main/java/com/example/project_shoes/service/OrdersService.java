package com.example.project_shoes.service;

import com.example.project_shoes.dto.OrdersDTO;
import com.example.project_shoes.dto.OrdersDetailsDTO;

import java.util.List;

public interface OrdersService {
    List<OrdersDTO> findAll();
    OrdersDTO findById(Long id);
    OrdersDTO save(OrdersDTO ordersDTO, List<OrdersDetailsDTO> orderDetails);
    OrdersDTO update(OrdersDTO ordersDTO);
    boolean delete(Long id);
    
    List<OrdersDTO> findByCustomerId(Long customerId);
} 