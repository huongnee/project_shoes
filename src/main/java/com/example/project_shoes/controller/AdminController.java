package com.example.project_shoes.controller;

import com.example.project_shoes.dto.CategoryDTO;
import com.example.project_shoes.dto.ConfigurationsDTO;
import com.example.project_shoes.dto.ProductDTO;
import com.example.project_shoes.dto.OrdersDTO;
import com.example.project_shoes.service.CategoryService;
import com.example.project_shoes.service.ConfigurationsService;
import com.example.project_shoes.service.ProductService;
import com.example.project_shoes.service.OrdersService;
import com.example.project_shoes.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ConfigurationsService configurationsService;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private OrdersService ordersService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index() {
        return "admin/index";
    }

    @GetMapping({"/products", "/products/list", "/products/list.html"})
    public String productsList(Model model) {
        try {
            List<ProductDTO> products = productService.findAllActive();
            System.out.println("Số lượng sản phẩm lấy được: " + products.size());
            
            if (products.isEmpty()) {
                System.out.println("Không có sản phẩm nào được tìm thấy từ cơ sở dữ liệu!");
                model.addAttribute("warning", "Không tìm thấy sản phẩm nào trong cơ sở dữ liệu. Vui lòng kiểm tra kết nối database hoặc thêm sản phẩm mới.");
            } else {
                for (ProductDTO product : products) {
                    System.out.println("Sản phẩm: " + product.getId() + " - " + product.getName() + 
                                      ", Danh mục: " + product.getCategoryName() + 
                                      ", Ảnh: " + product.getImage() + 
                                      ", Giá: " + product.getPrice() + 
                                      ", Số lượng: " + product.getQuantity() + 
                                      ", isActive: " + product.getIsActive() + 
                                      ", isDelete: " + product.getIsDelete());
                }
            }
            
            // Lấy danh sách danh mục cho bộ lọc
            List<CategoryDTO> categories = categoryService.findAllActive();
            model.addAttribute("categories", categories);
            
            model.addAttribute("products", products);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách sản phẩm: " + e.getMessage());
            model.addAttribute("products", new ArrayList<>());
        }
        return "admin/products/list";
    }
    
    @GetMapping("/products/add")
    public String productsAdd(Model model) {
        try {
            // Lấy danh sách danh mục để hiển thị trong dropdown
            List<CategoryDTO> categories = categoryService.findAllActive();
            if (categories.isEmpty()) {
                System.out.println("Không có danh mục nào được tìm thấy từ cơ sở dữ liệu!");
                model.addAttribute("warningCategory", "Không tìm thấy danh mục nào trong cơ sở dữ liệu.");
            } else {
                System.out.println("Số lượng danh mục lấy được: " + categories.size());
            }
            
            // Lấy danh sách cấu hình để hiển thị trong form
            List<ConfigurationsDTO> configurations = configurationsService.findAllActive();
            if (configurations.isEmpty()) {
                System.out.println("Không có cấu hình nào được tìm thấy từ cơ sở dữ liệu!");
                model.addAttribute("warningConfig", "Không tìm thấy cấu hình nào trong cơ sở dữ liệu.");
            } else {
                System.out.println("Số lượng cấu hình lấy được: " + configurations.size());
            }
            
            model.addAttribute("categories", categories);
            model.addAttribute("configurations", configurations);
            model.addAttribute("product", new ProductDTO()); // Truyền đối tượng rỗng để binding form
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy dữ liệu cho trang thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra khi tải dữ liệu: " + e.getMessage());
        }
        return "admin/products/add";
    }
    
    @PostMapping("/products/add")
    public String saveProduct(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Đang lưu sản phẩm: " + productDTO.getName());
            
            // Đặt giá trị mặc định
            if (productDTO.getIsActive() == null) {
                productDTO.setIsActive(true);
            }
            productDTO.setIsDelete(false);
            productDTO.setCreatedDate(LocalDateTime.now());
            // Giả sử admin có ID = 1
            productDTO.setCreatedBy(1L);
            
            ProductDTO savedProduct = productService.save(productDTO);
            System.out.println("Đã lưu sản phẩm: " + savedProduct.getId() + " - " + savedProduct.getName());
            
            redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được thêm thành công.");
            return "redirect:/admin/products/list";
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu sản phẩm: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu sản phẩm: " + e.getMessage());
            return "redirect:/admin/products/add";
        }
    }
    
    @GetMapping("/products/categories")
    public String productsCategories(Model model) {
        try {
            // Lấy tất cả danh mục
            List<CategoryDTO> categories = categoryService.findAllActive();
            if (categories.isEmpty()) {
                System.out.println("Không có danh mục nào được tìm thấy từ cơ sở dữ liệu!");
                model.addAttribute("warning", "Không tìm thấy danh mục nào trong cơ sở dữ liệu.");
            } else {
                System.out.println("Số lượng danh mục lấy được: " + categories.size());
                // Trả về danh sách chỉ có tên danh mục
                List<String> categoryNames = categories.stream()
                        .map(CategoryDTO::getName)
                        .collect(java.util.stream.Collectors.toList());
                model.addAttribute("categoryNames", categoryNames);
            }
            
            // Lấy danh mục gốc (không có parent)
            List<CategoryDTO> parentCategories = categoryService.findParentCategories();
            System.out.println("Số lượng danh mục gốc: " + parentCategories.size());
            
            model.addAttribute("categories", categories);
            model.addAttribute("parentCategories", parentCategories);
            model.addAttribute("category", new CategoryDTO()); // Truyền đối tượng rỗng để binding form
            model.addAttribute("showSimpleList", true); // Flag để hiển thị danh sách đơn giản
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách danh mục: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách danh mục: " + e.getMessage());
        }
        return "admin/products/categories";
    }
    
    @PostMapping("/products/categories")
    public String saveCategory(@ModelAttribute CategoryDTO categoryDTO, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Đang lưu danh mục: " + categoryDTO.getName());
            
            // Đặt giá trị mặc định
            if (categoryDTO.getIsActive() == null) {
                categoryDTO.setIsActive(true);
            }
            categoryDTO.setIsDelete(false);
            categoryDTO.setCreatedDate(LocalDateTime.now());
            // Giả sử admin có ID = 1
            categoryDTO.setCreatedBy(1L);
            
            // Tạo slug nếu không có
            if (categoryDTO.getSlug() == null || categoryDTO.getSlug().trim().isEmpty()) {
                String slug = categoryDTO.getName().toLowerCase()
                                .replaceAll("đ", "d")
                                .replaceAll("[^a-z0-9\\s]", "")
                                .replaceAll("\\s+", "-");
                categoryDTO.setSlug(slug);
            }
            
            CategoryDTO savedCategory = categoryService.save(categoryDTO);
            System.out.println("Đã lưu danh mục: " + savedCategory.getId() + " - " + savedCategory.getName());
            
            redirectAttributes.addFlashAttribute("success", "Danh mục đã được thêm thành công.");
            return "redirect:/admin/products/categories";
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu danh mục: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu danh mục: " + e.getMessage());
            return "redirect:/admin/products/categories";
        }
    }
    
    @GetMapping("/products/config")
    public String productsConfig(Model model) {
        try {
            // Lấy tất cả cấu hình
            List<ConfigurationsDTO> configurations = configurationsService.findAllActive();
            if (configurations.isEmpty()) {
                System.out.println("Không có cấu hình nào được tìm thấy từ cơ sở dữ liệu!");
                model.addAttribute("warning", "Không tìm thấy cấu hình nào trong cơ sở dữ liệu.");
            } else {
                System.out.println("Số lượng cấu hình lấy được: " + configurations.size());
            }
            
            model.addAttribute("configurations", configurations);
            model.addAttribute("configuration", new ConfigurationsDTO()); // Truyền đối tượng rỗng để binding form
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách cấu hình: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách cấu hình: " + e.getMessage());
        }
        return "admin/products/config";
    }
    
    @PostMapping("/products/config")
    public String saveConfiguration(@ModelAttribute ConfigurationsDTO configurationsDTO, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Đang lưu cấu hình: " + configurationsDTO.getName());
            
            // Đặt giá trị mặc định
            if (configurationsDTO.getIsActive() == null) {
                configurationsDTO.setIsActive(true);
            }
            configurationsDTO.setIsDelete(false);
            configurationsDTO.setCreatedDate(LocalDateTime.now());
            
            ConfigurationsDTO savedConfiguration = configurationsService.save(configurationsDTO);
            System.out.println("Đã lưu cấu hình: " + savedConfiguration.getId() + " - " + savedConfiguration.getName());
            
            redirectAttributes.addFlashAttribute("success", "Cấu hình đã được thêm thành công.");
            return "redirect:/admin/products/config";
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu cấu hình: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu cấu hình: " + e.getMessage());
            return "redirect:/admin/products/config";
        }
    }

    @GetMapping("/customers/list")
    public String customersList() {
        return "admin/customers/list";
    }

    // URL /admin/orders/list sẽ hiển thị trang danh sách đơn hàng
    @GetMapping("/orders/list")
    public String ordersListRedirect(Model model) {
        List<OrdersDTO> orders = ordersService.findAll();
        model.addAttribute("orders", orders);
        return "admin/orders/list";
    }

    // Quản lý đơn hàng
    @GetMapping("/orders")
    public String ordersList(Model model) {
        List<OrdersDTO> orders = ordersService.findAll();
        model.addAttribute("orders", orders);
        return "admin/orders/list";
    }
    
    @GetMapping("/orders/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        OrdersDTO order = ordersService.findById(id);
        if (order == null) {
            return "redirect:/admin/orders";
        }
        
        model.addAttribute("order", order);
        return "admin/orders/details";
    }
    
    @PostMapping("/orders/{id}/update-status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam Integer status, RedirectAttributes redirectAttributes) {
        OrdersDTO order = ordersService.findById(id);
        if (order == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng");
            return "redirect:/admin/orders/list";
        }
        
        order.setStatus(status);
        ordersService.update(order);
        
        String statusMessage = "Cập nhật trạng thái đơn hàng thành công";
        switch (status) {
            case 0:
                statusMessage = "Đơn hàng đã được chuyển sang trạng thái Chờ xác nhận";
                break;
            case 1:
                statusMessage = "Đơn hàng đã được xác nhận";
                break;
            case 2:
                statusMessage = "Đơn hàng đang được giao";
                break;
            case 3:
                statusMessage = "Đơn hàng đã được giao thành công";
                break;
            case 4:
                statusMessage = "Đơn hàng đã bị hủy";
                break;
        }
        
        redirectAttributes.addFlashAttribute("success", statusMessage);
        return "redirect:/admin/orders/" + id;
    }
    
    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = ordersService.delete(id);
        
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Xóa đơn hàng thành công");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa đơn hàng");
        }
        
        return "redirect:/admin/orders/list";
    }
} 