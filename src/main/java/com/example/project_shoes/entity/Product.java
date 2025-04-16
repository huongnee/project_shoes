package com.example.project_shoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IDCATEGORY", nullable = false)
    private Long idCategory;

    @ManyToOne
    @JoinColumn(name = "IDCATEGORY", insertable = false, updatable = false)
    private Category category;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "ISDELETE")
    private Boolean isDelete;

    @Column(name = "META_DESCRIPTION")
    private String metaDescription;

    @Column(name = "META_KEYWORD")
    private String metaKeyword;

    @Column(name = "META_TITLE")
    private String metaTitle;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "product")
    private List<ProductImages> productImages;

    @OneToMany(mappedBy = "product")
    private List<ProductConfig> productConfigs;

    @OneToMany(mappedBy = "product")
    private List<OrdersDetails> orderDetails;
} 