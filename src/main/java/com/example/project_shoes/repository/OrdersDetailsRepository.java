package com.example.project_shoes.repository;

import com.example.project_shoes.entity.OrdersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDetailsRepository extends JpaRepository<OrdersDetails, Long> {
    List<OrdersDetails> findByIdOrder(Long idOrder);
    OrdersDetails findByIdOrderAndIdProduct(Long idOrder, Long idProduct);
} 