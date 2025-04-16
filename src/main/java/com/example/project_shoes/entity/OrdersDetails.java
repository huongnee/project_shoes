package com.example.project_shoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_details")
public class OrdersDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDORD", nullable = false)
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "IDORD", insertable = false, updatable = false)
    private Orders order;

    @Column(name = "IDPRODUCT", nullable = false)
    private Long idProduct;

    @ManyToOne
    @JoinColumn(name = "IDPRODUCT", insertable = false, updatable = false)
    private Product product;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "QTY", nullable = false)
    private Integer qty;

    @Column(name = "RETURN_QTY")
    private Integer returnQty;

    @Column(name = "TOTAL", nullable = false)
    private BigDecimal total;
} 