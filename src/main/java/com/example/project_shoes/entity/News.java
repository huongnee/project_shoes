package com.example.project_shoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News {
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

    @Column(name = "IDNEWSCATEGORY", nullable = false)
    private Long idNewsCategory;

    @ManyToOne
    @JoinColumn(name = "IDNEWSCATEGORY", insertable = false, updatable = false)
    private NewsCategory newsCategory;

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

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;
} 