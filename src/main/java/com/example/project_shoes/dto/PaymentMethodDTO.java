package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {
    private Long id;
    private LocalDateTime createdDate;
    private Boolean isActive;
    private Boolean isDelete;
    private String name;
    private String notes;
    private LocalDateTime updatedDate;
} 