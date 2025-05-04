package com.example.project_shoes.dto;

import com.example.project_shoes.entity.OrdersDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private Long id;
    private String address;
    private String email;
    private String iOrders;
    private Long idCustomer;
    private String customerName;
    private Long idPayment;
    private String paymentMethodName;
    private Long idTransport;
    private String transportMethodName;
    private Boolean isActive;
    private Boolean isDelete;
    private String nameReceiver;
    private String notes;
    private LocalDateTime ordersDate;
    private String phone;
    private BigDecimal totalMoney;
    private Integer status; // 0: Chờ xác nhận, 1: Đã xác nhận, 2: Đang giao hàng, 3: Đã giao hàng, 4: Đã hủy
    private List<OrdersDetails> orderDetails;
} 