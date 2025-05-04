package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.CartDTO;
import com.example.project_shoes.dto.CartItemDTO;
import com.example.project_shoes.entity.Cart;
import com.example.project_shoes.entity.CartItem;
import com.example.project_shoes.entity.Product;
import com.example.project_shoes.repository.CartItemRepository;
import com.example.project_shoes.repository.CartRepository;
import com.example.project_shoes.repository.ProductRepository;
import com.example.project_shoes.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartDTO findByCustomerId(Long customerId) {
        Cart cart = cartRepository.findByCustomerIdAndIsDeleteFalse(customerId);
        if (cart == null) {
            return null;
        }
        return convertToDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO addToCart(Long customerId, Long productId, Integer quantity) {
        // Tìm hoặc tạo giỏ hàng mới cho khách hàng
        Cart cart = cartRepository.findByCustomerIdAndIsDeleteFalse(customerId);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
            cart.setCreatedDate(LocalDateTime.now());
            cart.setIsDelete(false);
            cart = cartRepository.save(cart);
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartItem cartItem = cartItemRepository.findByCartIdAndProductIdAndIsDeleteFalse(cart.getId(), productId);
        if (cartItem != null) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setUpdatedDate(LocalDateTime.now());
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới
            cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setCreatedDate(LocalDateTime.now());
            cartItem.setIsDelete(false);
        }
        cartItemRepository.save(cartItem);

        // Cập nhật ngày cập nhật giỏ hàng
        cart.setUpdatedDate(LocalDateTime.now());
        cartRepository.save(cart);

        return convertToDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(Long customerId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByCustomerIdAndIsDeleteFalse(customerId);
        if (cart == null) {
            return null;
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductIdAndIsDeleteFalse(cart.getId(), productId);
        if (cartItem != null) {
            if (quantity <= 0) {
                // Nếu số lượng <= 0, xóa sản phẩm khỏi giỏ hàng
                cartItem.setIsDelete(true);
            } else {
                // Cập nhật số lượng
                cartItem.setQuantity(quantity);
                cartItem.setUpdatedDate(LocalDateTime.now());
            }
            cartItemRepository.save(cartItem);

            // Cập nhật ngày cập nhật giỏ hàng
            cart.setUpdatedDate(LocalDateTime.now());
            cartRepository.save(cart);
        }

        return convertToDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO removeFromCart(Long customerId, Long productId) {
        Cart cart = cartRepository.findByCustomerIdAndIsDeleteFalse(customerId);
        if (cart == null) {
            return null;
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductIdAndIsDeleteFalse(cart.getId(), productId);
        if (cartItem != null) {
            cartItem.setIsDelete(true);
            cartItemRepository.save(cartItem);

            // Cập nhật ngày cập nhật giỏ hàng
            cart.setUpdatedDate(LocalDateTime.now());
            cartRepository.save(cart);
        }

        return convertToDTO(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long customerId) {
        log.info("Xóa toàn bộ giỏ hàng cho khách hàng: {}", customerId);
        List<CartItem> cartItems = cartItemRepository.findByCartCustomerIdAndIsDeleteFalse(customerId);
        for (CartItem item : cartItems) {
            item.setIsDelete(true);
            cartItemRepository.save(item);
        }
    }

    @Override
    @Transactional
    public void syncCartFromSession(Long customerId, Map<Long, Integer> sessionCart) {
        // Tìm hoặc tạo giỏ hàng mới cho khách hàng
        Cart cart = cartRepository.findByCustomerIdAndIsDeleteFalse(customerId);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
            cart.setCreatedDate(LocalDateTime.now());
            cart.setIsDelete(false);
            cart = cartRepository.save(cart);
        }

        // Đánh dấu các sản phẩm trong giỏ hiện tại là đã xóa
        List<CartItem> existingItems = cartItemRepository.findByCartIdAndIsDeleteFalse(cart.getId());
        for (CartItem item : existingItems) {
            item.setIsDelete(true);
            cartItemRepository.save(item);
        }

        // Thêm các sản phẩm từ session vào giỏ hàng
        for (Map.Entry<Long, Integer> entry : sessionCart.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            CartItem cartItem = cartItemRepository.findByCartIdAndProductIdAndIsDeleteFalse(cart.getId(), productId);
            if (cartItem != null) {
                // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
                cartItem.setQuantity(quantity);
                cartItem.setUpdatedDate(LocalDateTime.now());
                cartItem.setIsDelete(false);
            } else {
                // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới
                cartItem = new CartItem();
                cartItem.setCartId(cart.getId());
                cartItem.setProductId(productId);
                cartItem.setQuantity(quantity);
                cartItem.setCreatedDate(LocalDateTime.now());
                cartItem.setIsDelete(false);
            }
            cartItemRepository.save(cartItem);
        }

        // Cập nhật ngày cập nhật giỏ hàng
        cart.setUpdatedDate(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Override
    public List<CartItemDTO> getCartItems(Long customerId) {
        Cart cart = cartRepository.findByCustomerIdAndIsDeleteFalse(customerId);
        if (cart == null) {
            return new ArrayList<>();
        }

        List<CartItem> cartItems = cartItemRepository.findByCartIdAndIsDeleteFalse(cart.getId());
        return cartItems.stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setCustomerId(cart.getCustomerId());
        if (cart.getCustomer() != null) {
            dto.setCustomerName(cart.getCustomer().getName());
        }
        dto.setCreatedDate(cart.getCreatedDate());
        dto.setUpdatedDate(cart.getUpdatedDate());
        dto.setIsDelete(cart.getIsDelete());

        List<CartItem> cartItems = cartItemRepository.findByCartIdAndIsDeleteFalse(cart.getId());
        List<CartItemDTO> cartItemDTOS = cartItems.stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());
        dto.setCartItems(cartItemDTOS);

        return dto;
    }

    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setCartId(cartItem.getCartId());
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setCreatedDate(cartItem.getCreatedDate());
        dto.setUpdatedDate(cartItem.getUpdatedDate());
        dto.setIsDelete(cartItem.getIsDelete());

        Product product = cartItem.getProduct();
        if (product != null) {
            dto.setProductName(product.getName());
            dto.setProductImage(product.getImage());
            dto.setPrice(product.getPrice().doubleValue());
        }

        return dto;
    }
} 