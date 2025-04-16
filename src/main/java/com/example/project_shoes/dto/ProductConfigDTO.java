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
    private String configName;
    private Long idProduct;
    private String productName;
    private String value;
} 