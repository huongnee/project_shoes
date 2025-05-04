package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private Double price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isDelete;
} 