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
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IORDERS", nullable = false, length = 20)
    private String iOrders;

    @Column(name = "IDCUSTOMER", nullable = false)
    private Long idCustomer;

    @ManyToOne
    @JoinColumn(name = "IDCUSTOMER", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "IDPAYMENT", nullable = false)
    private Long idPayment;

    @ManyToOne
    @JoinColumn(name = "IDPAYMENT", insertable = false, updatable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "IDTRANSPORT", nullable = false)
    private Long idTransport;

    @ManyToOne
    @JoinColumn(name = "IDTRANSPORT", insertable = false, updatable = false)
    private TransportMethod transportMethod;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "ISDELETE")
    private Boolean isDelete;

    @Column(name = "NAME_RECEIVER")
    private String nameReceiver;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "ORDERS_DATE", nullable = false)
    private LocalDateTime ordersDate;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "STATUS", nullable = false)
    private Integer status; // 0: Chờ xác nhận, 1: Đã xác nhận, 2: Đang giao hàng, 3: Đã giao hàng, 4: Đã hủy

    @Column(name = "TOTAL_MONEY", nullable = false)
    private BigDecimal totalMoney;

    @OneToMany(mappedBy = "order")
    private List<OrdersDetails> orderDetails;
} 