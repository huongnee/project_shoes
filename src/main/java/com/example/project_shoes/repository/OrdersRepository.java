package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByIsDeleteFalse();
    Orders findByIdAndIsDeleteFalse(Long id);
    List<Orders> findByIdCustomerAndIsDeleteFalse(Long idCustomer);
    Orders findByiOrdersAndIsDeleteFalse(String iOrders);
} 