package com.example.project_shoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "ISDELETE")
    private Boolean isDelete;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Orders> orders;
} 