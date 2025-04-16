package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private Long id;
    private String contents;
    private Long createdBy;
    private LocalDateTime createdDate;
    private String description;
    private Long idNewsCategory;
    private String newsCategoryName;
    private String image;
    private Boolean isActive;
    private Boolean isDelete;
    private String metaDescription;
    private String metaKeyword;
    private String metaTitle;
    private String name;
    private String slug;
    private Long updatedBy;
    private LocalDateTime updatedDate;
} 