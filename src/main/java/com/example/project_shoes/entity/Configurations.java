package com.example.project_shoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configurations")
public class Configurations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "ISDELETE")
    private Boolean isDelete;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NOTES")
    private String notes;
} 