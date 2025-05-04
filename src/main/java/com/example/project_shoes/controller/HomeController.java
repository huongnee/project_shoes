package com.example.project_shoes.controller;

import com.example.project_shoes.dto.CategoryDTO;
import com.example.project_shoes.dto.CustomerDTO;
import com.example.project_shoes.dto.OrdersDTO;
import com.example.project_shoes.dto.OrdersDetailsDTO;
import com.example.project_shoes.dto.PaymentMethodDTO;
import com.example.project_shoes.dto.ProductDTO;
import com.example.project_shoes.dto.TransportMethodDTO;
import com.example.project_shoes.dto.CartItemDTO;
import com.example.project_shoes.entity.Category;
import com.example.project_shoes.entity.Customer;
import com.example.project_shoes.entity.Orders;
import com.example.project_shoes.entity.OrdersDetails;
import com.example.project_shoes.entity.PaymentMethod;
import com.example.project_shoes.entity.TransportMethod;
import com.example.project_shoes.service.CategoryService;
import com.example.project_shoes.service.CustomerService;
import com.example.project_shoes.service.OrdersService;
import com.example.project_shoes.service.PaymentMethodService;
import com.example.project_shoes.service.ProductService;
import com.example.project_shoes.service.TransportMethodService;
import com.example.project_shoes.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private TransportMethodService transportMethodService;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDTO> products = productService.findAllActive();
        List<CategoryDTO> categories = categoryService.findAllActive();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "home";
    }
    
    @GetMapping("/products")
    public String productList(Model model) {
        List<ProductDTO> products = productService.findAllActive();
        List<CategoryDTO> categories = categoryService.findAllActive();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "products";
    }

    @GetMapping("/products/category/{categoryId}")
    public String productsByCategory(@PathVariable Long categoryId, Model model) {
        List<ProductDTO> products = productService.findByCategoryId(categoryId);
        List<CategoryDTO> categories = categoryService.findAllActive();
        CategoryDTO currentCategory = categoryService.findById(categoryId);
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("currentCategory", currentCategory);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductDTO product = productService.findById(id);
        if (product == null) {
            return "redirect:/products";
        }
        
        List<CategoryDTO> categories = categoryService.findAllActive();
        List<ProductDTO> relatedProducts = productService.findByCategoryId(product.getIdCategory());
        
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("relatedProducts", relatedProducts);
        return "product-details";
    }
    
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<ProductDTO> products = productService.searchByName(keyword);
        List<CategoryDTO> categories = categoryService.findAllActive();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        return "products";
    }
    
    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        List<ProductDTO> cartItems = new ArrayList<>();
        double total = 0;
        
        if (customerId != null) {
            // Người dùng đã đăng nhập, lấy giỏ hàng từ database
            List<CartItemDTO> dbCartItems = cartService.getCartItems(customerId);
            
            for (CartItemDTO item : dbCartItems) {
                ProductDTO product = productService.findById(item.getProductId());
                if (product != null) {
                    product.setQuantity(item.getQuantity());
                    cartItems.add(product);
                    total += product.getPrice().doubleValue() * item.getQuantity();
                }
            }
        } else {
            // Người dùng chưa đăng nhập, lấy giỏ hàng từ session
            Map<Long, Integer> cart = getCart(session);
            
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                ProductDTO product = productService.findById(entry.getKey());
                if (product != null) {
                    product.setQuantity(entry.getValue());
                    cartItems.add(product);
                    total += product.getPrice().doubleValue() * entry.getValue();
                }
            }
        }
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart";
    }
    
    @PostMapping("/cart/add/{productId}")
    @ResponseBody
    public Map<String, Object> addToCart(@PathVariable Long productId, @RequestParam Integer quantity, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        Map<String, Object> response = new HashMap<>();
        
        if (customerId != null) {
            // Người dùng đã đăng nhập, sử dụng CartService để thêm vào database
            cartService.addToCart(customerId, productId, quantity);
            
            // Lấy số lượng sản phẩm trong giỏ hàng từ database
            List<CartItemDTO> cartItems = cartService.getCartItems(customerId);
            response.put("success", true);
            response.put("cartSize", cartItems.size());
        } else {
            // Người dùng chưa đăng nhập, lưu vào session
            Map<Long, Integer> cart = getCart(session);
            
            // Cập nhật giỏ hàng
            if (cart.containsKey(productId)) {
                cart.put(productId, cart.get(productId) + quantity);
            } else {
                cart.put(productId, quantity);
            }
            
            session.setAttribute("cart", cart);
            response.put("success", true);
            response.put("cartSize", cart.size());
        }
        
        return response;
    }
    
    @PostMapping("/cart/update")
    @ResponseBody
    public Map<String, Object> updateCart(@RequestBody Map<Long, Integer> updates, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        Map<String, Object> response = new HashMap<>();
        
        if (customerId != null) {
            // Người dùng đã đăng nhập, sử dụng CartService để cập nhật database
            for (Map.Entry<Long, Integer> entry : updates.entrySet()) {
                if (entry.getValue() <= 0) {
                    cartService.removeFromCart(customerId, entry.getKey());
                } else {
                    cartService.updateCartItem(customerId, entry.getKey(), entry.getValue());
                }
            }
        } else {
            // Người dùng chưa đăng nhập, cập nhật session
            Map<Long, Integer> cart = getCart(session);
            
            for (Map.Entry<Long, Integer> entry : updates.entrySet()) {
                if (entry.getValue() <= 0) {
                    cart.remove(entry.getKey());
                } else {
                    cart.put(entry.getKey(), entry.getValue());
                }
            }
            
            session.setAttribute("cart", cart);
        }
        
        response.put("success", true);
        return response;
    }
    
    @PostMapping("/cart/remove/{productId}")
    @ResponseBody
    public Map<String, Object> removeFromCart(@PathVariable Long productId, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        Map<String, Object> response = new HashMap<>();
        
        if (customerId != null) {
            // Người dùng đã đăng nhập, sử dụng CartService để xóa khỏi database
            cartService.removeFromCart(customerId, productId);
            
            // Lấy số lượng sản phẩm trong giỏ hàng từ database
            List<CartItemDTO> cartItems = cartService.getCartItems(customerId);
            response.put("success", true);
            response.put("cartSize", cartItems.size());
        } else {
            // Người dùng chưa đăng nhập, xóa khỏi session
            Map<Long, Integer> cart = getCart(session);
            cart.remove(productId);
            session.setAttribute("cart", cart);
            
            response.put("success", true);
            response.put("cartSize", cart.size());
        }
        
        return response;
    }
    
    @GetMapping("/my-orders")
    public String myOrders(Model model, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        List<OrdersDTO> orders = ordersService.findByCustomerId(customerId);
        model.addAttribute("orders", orders);
        return "my-orders";
    }
    
    @GetMapping("/my-orders/{orderId}")
    public String orderDetails(@PathVariable Long orderId, Model model, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        OrdersDTO order = ordersService.findById(orderId);
        if (order == null || !order.getIdCustomer().equals(customerId)) {
            return "redirect:/my-orders";
        }
        
        model.addAttribute("order", order);
        return "order-details";
    }
    
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login?redirect=checkout";
        }
        
        List<ProductDTO> cartItems = new ArrayList<>();
        double total = 0;
        
        // Lấy giỏ hàng từ database thay vì session
        List<CartItemDTO> dbCartItems = cartService.getCartItems(customerId);
        if (dbCartItems.isEmpty()) {
            return "redirect:/cart";
        }
        
        for (CartItemDTO item : dbCartItems) {
            ProductDTO product = productService.findById(item.getProductId());
            if (product != null) {
                product.setQuantity(item.getQuantity());
                cartItems.add(product);
                total += product.getPrice().doubleValue() * item.getQuantity();
            }
        }
        
        // Lấy thông tin khách hàng
        CustomerDTO customerInfo = customerService.findById(customerId);
        model.addAttribute("customerInfo", customerInfo);
        
        // Lấy phương thức thanh toán và vận chuyển
        List<PaymentMethod> paymentMethods = paymentMethodService.findAllActive();
        List<TransportMethod> transportMethods = transportMethodService.findAllActive();
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("paymentMethods", paymentMethods);
        model.addAttribute("transportMethods", transportMethods);
        
        return "checkout";
    }
    
    @PostMapping("/checkout/place-order")
    public String placeOrder(
            @RequestParam String nameReceiver,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(required = false) String notes,
            @RequestParam Long idPayment,
            @RequestParam Long idTransport,
            @RequestParam Long idCustomer,
            @RequestParam BigDecimal totalMoney,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        // Kiểm tra đăng nhập
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null || !customerId.equals(idCustomer)) {
            return "redirect:/login";
        }
        
        // Lấy giỏ hàng từ database
        List<CartItemDTO> dbCartItems = cartService.getCartItems(customerId);
        if (dbCartItems.isEmpty()) {
            return "redirect:/cart";
        }
        
        try {
            // Tạo đơn hàng mới
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.setNameReceiver(nameReceiver);
            ordersDTO.setEmail(email);
            ordersDTO.setPhone(phone);
            ordersDTO.setAddress(address);
            ordersDTO.setNotes(notes);
            ordersDTO.setIdPayment(idPayment);
            ordersDTO.setIdTransport(idTransport);
            ordersDTO.setIdCustomer(idCustomer);
            ordersDTO.setTotalMoney(totalMoney);
            
            // Tạo chi tiết đơn hàng từ database
            List<OrdersDetailsDTO> orderDetails = new ArrayList<>();
            
            for (CartItemDTO item : dbCartItems) {
                ProductDTO product = productService.findById(item.getProductId());
                if (product != null) {
                    OrdersDetailsDTO detailDTO = new OrdersDetailsDTO();
                    detailDTO.setIdProduct(product.getId());
                    detailDTO.setQty(item.getQuantity());
                    detailDTO.setPrice(product.getPrice());
                    detailDTO.setTotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    orderDetails.add(detailDTO);
                }
            }
            
            // Lưu đơn hàng
            OrdersDTO savedOrder = ordersService.save(ordersDTO, orderDetails);
            
            // Xóa giỏ hàng sau khi đặt hàng thành công
            cartService.clearCart(customerId);
            session.removeAttribute("cart");
            
            redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công! Mã đơn hàng của bạn là " + savedOrder.getIOrders());
            return "redirect:/my-orders";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }
    
    @PostMapping("/my-orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId, HttpSession session, RedirectAttributes redirectAttributes) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        OrdersDTO order = ordersService.findById(orderId);
        if (order == null || !order.getIdCustomer().equals(customerId)) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng");
            return "redirect:/my-orders";
        }
        
        // Chỉ cho phép hủy đơn hàng khi đơn hàng ở trạng thái chờ xác nhận
        if (order.getStatus() != 0) {
            redirectAttributes.addFlashAttribute("error", "Không thể hủy đơn hàng ở trạng thái hiện tại");
            return "redirect:/my-orders/" + orderId;
        }
        
        // Cập nhật trạng thái sang hủy
        order.setStatus(4); // 4: Đã hủy
        ordersService.update(order);
        
        redirectAttributes.addFlashAttribute("success", "Đơn hàng đã được hủy thành công");
        return "redirect:/my-orders";
    }
    
    @SuppressWarnings("unchecked")
    private Map<Long, Integer> getCart(HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
} 