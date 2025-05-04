package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isDelete;
    private List<CartItemDTO> cartItems;
} 