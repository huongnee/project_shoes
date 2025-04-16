package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImagesDTO {
    private Long id;
    private Long idProduct;
    private String productName;
    private String name;
    private String urlImg;
} 