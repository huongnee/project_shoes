package com.example.project_shoes.config;

import com.example.project_shoes.dto.CartItemDTO;
import com.example.project_shoes.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ShoppingCartInterceptor implements HandlerInterceptor {

    @Autowired
    private CartService cartService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            HttpSession session = request.getSession();
            Long customerId = (Long) session.getAttribute("customerId");
            int cartSize = 0;
            
            if (customerId != null) {
                // Người dùng đã đăng nhập, lấy số lượng sản phẩm từ database
                List<CartItemDTO> cartItems = cartService.getCartItems(customerId);
                cartSize = cartItems.size();
                log.debug("Người dùng đã đăng nhập (ID: {}). Số lượng sản phẩm trong giỏ hàng: {}", customerId, cartSize);
            } else {
                // Người dùng chưa đăng nhập, lấy số lượng sản phẩm từ session
                @SuppressWarnings("unchecked")
                Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
                if (cart != null) {
                    cartSize = cart.size();
                    log.debug("Người dùng chưa đăng nhập. Số lượng sản phẩm trong giỏ hàng: {}", cartSize);
                }
            }
            
            // Thêm thông tin giỏ hàng vào model để hiển thị trên tất cả các trang
            modelAndView.addObject("cartSize", cartSize);
        }
    }
} 