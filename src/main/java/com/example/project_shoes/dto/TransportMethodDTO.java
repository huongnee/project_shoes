package com.example.project_shoes.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransportMethodDTO {
    private Long id;
    private String name;
    private String notes;
    private Boolean isActive;
    private Boolean isDelete;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
} 