package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByIdCustomerAndIsDeleteFalse(Long idCustomer);
    List<Orders> findByIsDeleteFalse();
    Orders findByIdAndIsDeleteFalse(Long id);
    Orders findByiOrdersAndIsDeleteFalse(String iOrders);

    long countByIsDeleteFalse();

    long countByStatusAndIsDeleteFalse(int status);

    @Query("SELECT SUM(o.totalMoney) FROM Orders o WHERE o.status = :status AND o.isDelete = false")
    BigDecimal sumTotalPriceByStatusAndIsDeleteFalse(@Param("status") int status);

    @Query("SELECT SUM(o.totalMoney) FROM Orders o WHERE o.ordersDate BETWEEN :startDate AND :endDate AND o.status = :status AND o.isDelete = false")
    BigDecimal sumTotalPriceByOrdersDateBetweenAndStatusAndIsDeleteFalse(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate, 
            @Param("status") int status);

    List<Orders> findTop5ByIsDeleteFalseOrderByOrdersDateDesc();

    @Query("SELECT SUM(o.totalMoney) FROM Orders o WHERE o.isDelete = false")
    BigDecimal sumTotalRevenue();
} 