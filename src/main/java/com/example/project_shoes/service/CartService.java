package com.example.project_shoes.service;

import com.example.project_shoes.dto.CartDTO;
import com.example.project_shoes.dto.CartItemDTO;

import java.util.List;

public interface CartService {
    CartDTO findByCustomerId(Long customerId);
    CartDTO addToCart(Long customerId, Long productId, Integer quantity);
    CartDTO updateCartItem(Long customerId, Long productId, Integer quantity);
    CartDTO removeFromCart(Long customerId, Long productId);
    /**
     * Xóa tất cả sản phẩm trong giỏ hàng của khách hàng
     * 
     * @param customerId ID của khách hàng
     */
    void clearCart(Long customerId);
    void syncCartFromSession(Long customerId, java.util.Map<Long, Integer> sessionCart);
    List<CartItemDTO> getCartItems(Long customerId);
} 