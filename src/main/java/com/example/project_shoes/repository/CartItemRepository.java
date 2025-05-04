package com.example.project_shoes.repository;

import com.example.project_shoes.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartIdAndIsDeleteFalse(Long cartId);
    CartItem findByCartIdAndProductIdAndIsDeleteFalse(Long cartId, Long productId);
    void deleteByCartId(Long cartId);
    List<CartItem> findByCartCustomerIdAndIsDeleteFalse(Long customerId);
} 