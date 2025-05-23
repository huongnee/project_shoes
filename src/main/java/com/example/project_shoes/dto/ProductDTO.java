package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String contents;
    private Long createdBy;
    private LocalDateTime createdDate;
    private String description;
    private Long idCategory;
    private String categoryName; // Tên danh mục, không lưu trữ trong cơ sở dữ liệu
    private String image;
    private Boolean isActive;
    private Boolean isDelete;
    private String metaDescription;
    private String metaKeyword;
    private String metaTitle;
    private String name;
    private String notes;
    private BigDecimal price;
    private Integer quantity;
    private String slug;
    private Long updatedBy;
    private LocalDateTime updatedDate;
    private List<ProductImagesDTO> productImages;
    private List<MultipartFile> imageFiles; // Thêm field này để xử lý file upload
    private List<ProductConfigDTO> productConfigs; // Thêm danh sách cấu hình
} 