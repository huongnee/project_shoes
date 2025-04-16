package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
} 