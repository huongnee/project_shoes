package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsCategoryDTO {
    private Long id;
    private Long createdBy;
    private LocalDateTime createdDate;
    private String icon;
    private Long idParent;
    private Boolean isActive;
    private Boolean isDelete;
    private String metaDescription;
    private String metaKeyword;
    private String metaTitle;
    private String name;
    private String notes;
    private String slug;
    private Long updatedBy;
    private LocalDateTime updatedDate;
} 