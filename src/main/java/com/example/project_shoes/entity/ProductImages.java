package com.example.project_shoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDPRODUCT", nullable = false)
    private Long idProduct;

    @ManyToOne
    @JoinColumn(name = "IDPRODUCT", insertable = false, updatable = false)
    private Product product;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URLIMG")
    private String urlImg;
} 