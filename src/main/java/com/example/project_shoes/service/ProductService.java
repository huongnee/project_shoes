package com.example.project_shoes.service;

import com.example.project_shoes.dto.ProductDTO;
import com.example.project_shoes.dto.ProductImagesDTO;
import com.example.project_shoes.dto.ProductConfigDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllActive();
    List<ProductDTO> searchByName(String name);
    ProductDTO findById(Long id);
    List<ProductDTO> findByCategoryId(Long categoryId);
    ProductDTO save(ProductDTO productDTO);
    void delete(Long id);
    void updateMainImage(Long productId, String imageUrl);
    void saveProductImages(List<ProductImagesDTO> images);
    void saveProductConfigs(List<ProductConfigDTO> configs);
} 