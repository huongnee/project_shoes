package com.example.project_shoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_config")
public class ProductConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDCONFIG", nullable = false)
    private Long idConfig;

    @ManyToOne
    @JoinColumn(name = "IDCONFIG", insertable = false, updatable = false)
    private Configurations configuration;

    @Column(name = "IDPRODUCT", nullable = false)
    private Long idProduct;

    @ManyToOne
    @JoinColumn(name = "IDPRODUCT", insertable = false, updatable = false)
    private Product product;

    @Column(name = "VALUE")
    private String value;
} 