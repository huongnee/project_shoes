package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String address;
    private String avatar;
    private Long createdBy;
    private LocalDateTime createdDate;
    private String email;
    private Boolean isActive;
    private Boolean isDelete;
    private String name;
    private String password;
    private String phone;
    private Long updatedBy;
    private LocalDateTime updatedDate;
    private String username;
    private Integer totalOrders = 0;
} 