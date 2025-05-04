package com.example.project_shoes.controller;

import com.example.project_shoes.dto.CustomerDTO;
import com.example.project_shoes.dto.CartItemDTO;
import com.example.project_shoes.entity.Customer;
import com.example.project_shoes.service.CustomerService;
import com.example.project_shoes.service.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, 
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        System.out.println("Login attempt - Email: " + email);
        System.out.println("Password length: " + (password != null ? password.length() : 0));
        
        CustomerDTO customer = customerService.findByEmailAndPassword(email, password);
        
        if (customer != null) {
            System.out.println("Login successful for: " + email);
            // Lưu thông tin customer vào session
            session.setAttribute("customerId", customer.getId());
            session.setAttribute("customerName", customer.getName());
            session.setAttribute("customerEmail", customer.getEmail());
            
            // Đồng bộ giỏ hàng từ session vào database
            try {
                @SuppressWarnings("unchecked")
                Map<Long, Integer> sessionCart = (Map<Long, Integer>) session.getAttribute("cart");
                if (sessionCart != null && !sessionCart.isEmpty()) {
                    cartService.syncCartFromSession(customer.getId(), sessionCart);
                }
            } catch (Exception e) {
                System.err.println("Error syncing cart: " + e.getMessage());
            }
            
            return "redirect:/";
        } else {
            System.out.println("Login failed for: " + email);
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Lưu giỏ hàng từ database vào session trước khi đăng xuất
        try {
            Long customerId = (Long) session.getAttribute("customerId");
            if (customerId != null) {
                List<CartItemDTO> cartItems = cartService.getCartItems(customerId);
                if (cartItems != null && !cartItems.isEmpty()) {
                    Map<Long, Integer> sessionCart = new HashMap<>();
                    for (CartItemDTO item : cartItems) {
                        sessionCart.put(item.getProductId(), item.getQuantity());
                    }
                    session.setAttribute("cart", sessionCart);
                }
            }
        } catch (Exception e) {
            System.err.println("Error saving cart to session: " + e.getMessage());
        }
        
        // Xóa thông tin customer khỏi session
        session.removeAttribute("customerId");
        session.removeAttribute("customerName");
        session.removeAttribute("customerEmail");
        
        return "redirect:/login?logout=true";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "register";
    }
    
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("customer") CustomerDTO customerDTO,
                                 BindingResult result,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        
        System.out.println("Register attempt - Email: " + customerDTO.getEmail());
        System.out.println("Original password: " + customerDTO.getPassword()); 
        
        // Kiểm tra xác nhận mật khẩu
        if (!customerDTO.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Mật khẩu xác nhận không khớp");
        }
        
        // Kiểm tra email đã tồn tại
        if (customerService.existsByEmail(customerDTO.getEmail())) {
            result.rejectValue("email", "error.user", "Email đã được sử dụng");
        }
        
        if (result.hasErrors()) {
            return "register";
        }
        
        // Mặc định là active và không xóa
        customerDTO.setIsActive(true);
        customerDTO.setIsDelete(false);
        
        CustomerDTO savedCustomer = customerService.save(customerDTO);
        System.out.println("Customer saved with ID: " + (savedCustomer != null ? savedCustomer.getId() : "null"));
        
        redirectAttributes.addAttribute("registered", "true");
        return "redirect:/login";
    }
    
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        CustomerDTO customer = customerService.findByEmail(email);
        
        if (customer != null) {
            // Gửi email đặt lại mật khẩu (không triển khai trong phạm vi này)
            redirectAttributes.addAttribute("success", "Vui lòng kiểm tra email của bạn để đặt lại mật khẩu");
        } else {
            redirectAttributes.addAttribute("error", "Không tìm thấy tài khoản với email này");
        }
        
        return "redirect:/forgot-password";
    }
    
    @GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        CustomerDTO customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);
        
        return "profile";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("customer") CustomerDTO customerDTO,
                               BindingResult result,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        // Giữ nguyên ID người dùng hiện tại
        customerDTO.setId(customerId);
        
        if (result.hasErrors()) {
            return "profile";
        }
        
        customerService.update(customerDTO);
        
        // Cập nhật thông tin trong session
        session.setAttribute("customerName", customerDTO.getName());
        session.setAttribute("customerEmail", customerDTO.getEmail());
        
        redirectAttributes.addAttribute("success", "Cập nhật thông tin thành công");
        return "redirect:/profile";
    }
} 