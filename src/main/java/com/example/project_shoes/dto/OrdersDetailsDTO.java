package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDetailsDTO {
    private Long id;
    private Long idOrder;
    private String orderId;
    private Long idProduct;
    private String productName;
    private BigDecimal price;
    private Integer qty;
    private Integer returnQty;
    private BigDecimal total;
} 