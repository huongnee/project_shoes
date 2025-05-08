package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationsDTO {
    private Long id;
    private String name;
    private String notes;
    private Boolean isActive;
    private Boolean isDelete;
} 