package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfigDTO {
    private Long id;
    private Long idConfig;
    private Long idProduct;
    private String value;
    private String configName; // Tên cấu hình, không lưu vào DB
    private String productName;
} 