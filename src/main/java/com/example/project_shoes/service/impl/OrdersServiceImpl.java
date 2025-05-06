package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.OrdersDTO;
import com.example.project_shoes.dto.OrdersDetailsDTO;
import com.example.project_shoes.entity.Orders;
import com.example.project_shoes.entity.OrdersDetails;
import com.example.project_shoes.repository.OrdersRepository;
import com.example.project_shoes.repository.OrdersDetailsRepository;
import com.example.project_shoes.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    
    @Autowired
    private OrdersDetailsRepository ordersDetailsRepository;
    
    @Override
    public List<OrdersDTO> findAll() {
        List<Orders> orders = ordersRepository.findByIsDeleteFalse();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrdersDTO findById(Long id) {
        return ordersRepository.findById(id)
                .map(order -> {
                    OrdersDTO dto = convertToDTO(order);
                    // Lấy thông tin chi tiết đơn hàng
                    List<OrdersDetails> orderDetailsList = ordersDetailsRepository.findByIdOrder(id);
                    dto.setOrderDetails(orderDetailsList);
                    return dto;
                })
                .orElse(null);
    }
    
    @Override
    public OrdersDTO save(OrdersDTO ordersDTO, List<OrdersDetailsDTO> orderDetails) {
        Orders orders = convertToEntity(ordersDTO);
        if (orders.getId() == null) {
            orders.setOrdersDate(LocalDateTime.now());
            // Tạo một mã đơn hàng duy nhất
            orders.setIOrders("ORD" + System.currentTimeMillis() % 1000000);
            // Mặc định trạng thái là chờ xác nhận
            orders.setStatus(0);
        }
        
        // Mặc định là active và không xóa
        orders.setIsActive(true);
        orders.setIsDelete(false);
        
        Orders savedOrders = ordersRepository.save(orders);
        
        // Lưu chi tiết đơn hàng
        for (OrdersDetailsDTO detailDTO : orderDetails) {
            OrdersDetails detail = new OrdersDetails();
            detail.setIdOrder(savedOrders.getId());
            detail.setIdProduct(detailDTO.getIdProduct());
            detail.setPrice(detailDTO.getPrice());
            detail.setQty(detailDTO.getQty());
            detail.setTotal(detailDTO.getTotal());
            detail.setReturnQty(0); // Mặc định là 0
            
            ordersDetailsRepository.save(detail);
        }
        
        return convertToDTO(savedOrders);
    }
    
    @Override
    public OrdersDTO update(OrdersDTO ordersDTO) {
        Orders orders = convertToEntity(ordersDTO);
        Orders savedOrders = ordersRepository.save(orders);
        return convertToDTO(savedOrders);
    }
    
    @Override
    public boolean delete(Long id) {
        Orders orders = ordersRepository.findById(id).orElse(null);
        if (orders != null) {
            // Soft delete
            orders.setIsDelete(true);
            ordersRepository.save(orders);
            return true;
        }
        return false;
    }
    
    @Override
    public List<OrdersDTO> findByCustomerId(Long customerId) {
        List<Orders> orders = ordersRepository.findByIdCustomerAndIsDeleteFalse(customerId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrdersDTO> findAllActive() {
        List<Orders> orders = ordersRepository.findByIsDeleteFalse();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    @Override
    public List<OrdersDTO> findRecentOrders(int limit) {
        List<Orders> recentOrders = ordersRepository.findTop5ByIsDeleteFalseOrderByOrdersDateDesc();
        return recentOrders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    private OrdersDTO convertToDTO(Orders orders) {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(orders.getId());
        dto.setAddress(orders.getAddress());
        dto.setEmail(orders.getEmail());
        dto.setIOrders(orders.getIOrders());
        dto.setIdCustomer(orders.getIdCustomer());
        
        if (orders.getCustomer() != null) {
            dto.setCustomerName(orders.getCustomer().getName());
        }
        
        dto.setIdPayment(orders.getIdPayment());
        
        if (orders.getPaymentMethod() != null) {
            dto.setPaymentMethodName(orders.getPaymentMethod().getName());
        }
        
        dto.setIdTransport(orders.getIdTransport());
        
        if (orders.getTransportMethod() != null) {
            dto.setTransportMethodName(orders.getTransportMethod().getName());
        }
        
        // Boolean handling - chuyển đổi từ Boolean sang boolean rồi lại sang Boolean
        // để tránh vấn đề với giá trị null trong Java và MySQL
        dto.setIsActive(orders.getIsActive() != null ? orders.getIsActive() : false);
        dto.setIsDelete(orders.getIsDelete() != null ? orders.getIsDelete() : false);
        
        dto.setNameReceiver(orders.getNameReceiver());
        dto.setNotes(orders.getNotes());
        dto.setOrdersDate(orders.getOrdersDate());
        dto.setPhone(orders.getPhone());
        dto.setTotalMoney(orders.getTotalMoney());
        
        // Thêm status
        dto.setStatus(orders.getStatus() != null ? orders.getStatus() : 0);
        
        return dto;
    }
    
    private Orders convertToEntity(OrdersDTO dto) {
        Orders orders = new Orders();
        orders.setId(dto.getId());
        orders.setAddress(dto.getAddress());
        orders.setEmail(dto.getEmail());
        orders.setIOrders(dto.getIOrders());
        orders.setIdCustomer(dto.getIdCustomer());
        orders.setIdPayment(dto.getIdPayment());
        orders.setIdTransport(dto.getIdTransport());
        
        // Boolean handling để tránh vấn đề với giá trị null
        orders.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : false);
        orders.setIsDelete(dto.getIsDelete() != null ? dto.getIsDelete() : false);
        
        orders.setNameReceiver(dto.getNameReceiver());
        orders.setNotes(dto.getNotes());
        orders.setOrdersDate(dto.getOrdersDate());
        orders.setPhone(dto.getPhone());
        orders.setTotalMoney(dto.getTotalMoney());
        
        // Thêm status
        orders.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        
        return orders;
    }
} 