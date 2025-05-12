package com.example.project_shoes.controller;

import com.example.project_shoes.dto.*;
import com.example.project_shoes.service.CategoryService;
import com.example.project_shoes.service.ConfigurationsService;
import com.example.project_shoes.service.ProductService;
import com.example.project_shoes.service.OrdersService;
import com.example.project_shoes.service.CustomerService;
import com.example.project_shoes.service.TransportMethodService;
import com.example.project_shoes.service.PaymentMethodService;
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
// Thêm import cho NewsService và NewsDTO
import com.example.project_shoes.service.NewsService;

import com.example.project_shoes.service.NewsCategoryService;

import com.example.project_shoes.service.DashboardService;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.Map;

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

    @Autowired
    private TransportMethodService transportMethodService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsCategoryService newsCategoryService; // Service lấy danh mục

    @Autowired
private DashboardService dashboardService;

@GetMapping("/admin/index")
public String dashboard(Model model) {
    // ... các dữ liệu tổng quan khác ...
    model.addAttribute("dashboard", dashboardService.getDashboardStats());
    model.addAttribute("activeMenu", "home");
    return "admin/index";
}



    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(Model model) {
        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("dashboard", dashboardService.getDashboardStats());
        return "admin/index";
    }

    @GetMapping({"/products", "/products/list", "/products/list.html"})
    public String productsList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sort,
            Model model) {
        model.addAttribute("activeMenu", "products");
        model.addAttribute("activeSubMenu", "products-list");
        try {
            List<ProductDTO> products;
            
            // Xử lý tìm kiếm
            if (keyword != null && !keyword.trim().isEmpty()) {
                products = productService.searchByName(keyword.trim());
                model.addAttribute("keyword", keyword);
            } else {
                products = productService.findAllActive();
            }
            
            // Xử lý lọc theo danh mục
            if (category != null) {
                products = products.stream()
                        .filter(p -> p.getIdCategory().equals(category))
                        .collect(Collectors.toList());
            }
            
            // Xử lý lọc theo giá
            if (price != null) {
                BigDecimal price1M = new BigDecimal("1000000");
                BigDecimal price2M = new BigDecimal("2000000");
                BigDecimal price3M = new BigDecimal("3000000");
                
                switch (price) {
                    case "1":
                        products = products.stream()
                                .filter(p -> p.getPrice().compareTo(price1M) < 0)
                                .collect(Collectors.toList());
                        break;
                    case "2":
                        products = products.stream()
                                .filter(p -> p.getPrice().compareTo(price1M) >= 0 && p.getPrice().compareTo(price2M) < 0)
                                .collect(Collectors.toList());
                        break;
                    case "3":
                        products = products.stream()
                                .filter(p -> p.getPrice().compareTo(price2M) >= 0 && p.getPrice().compareTo(price3M) < 0)
                                .collect(Collectors.toList());
                        break;
                    case "4":
                        products = products.stream()
                                .filter(p -> p.getPrice().compareTo(price3M) >= 0)
                                .collect(Collectors.toList());
                        break;
                }
            }
            
            // Xử lý lọc theo trạng thái
            if (status != null) {
                switch (status) {
                    case "instock":
                        products = products.stream()
                                .filter(p -> p.getQuantity() > 0)
                                .collect(Collectors.toList());
                        break;
                    case "outofstock":
                        products = products.stream()
                                .filter(p -> p.getQuantity() == 0)
                                .collect(Collectors.toList());
                        break;
                }
            }
            
            // Xử lý sắp xếp
            if (sort != null) {
                switch (sort) {
                    case "name_asc":
                        products.sort((a, b) -> a.getName().compareTo(b.getName()));
                        break;
                    case "name_desc":
                        products.sort((a, b) -> b.getName().compareTo(a.getName()));
                        break;
                    case "price_asc":
                        products.sort((a, b) -> a.getPrice().compareTo(b.getPrice()));
                        break;
                    case "price_desc":
                        products.sort((a, b) -> b.getPrice().compareTo(a.getPrice()));
                        break;
                }
            }

            if (products.isEmpty()) {
                model.addAttribute("warning", "Không tìm thấy sản phẩm nào phù hợp với điều kiện tìm kiếm.");
            }
            
            // Lấy danh sách danh mục cho bộ lọc
            List<CategoryDTO> categories = categoryService.findAllActive();
            model.addAttribute("categories", categories);
            model.addAttribute("products", products);
            
            // Thêm các tham số lọc vào model để duy trì trạng thái
            model.addAttribute("selectedCategory", category);
            model.addAttribute("selectedPrice", price);
            model.addAttribute("selectedStatus", status);
            model.addAttribute("selectedSort", sort);
            
            return "admin/products/list";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách sản phẩm: " + e.getMessage());
            return "admin/products/list";
        }
    }
    
    @GetMapping("/products/add")
    public String productsAdd(Model model) {
        model.addAttribute("activeMenu", "products");
        model.addAttribute("activeSubMenu", "products-add");
        model.addAttribute("product", new ProductDTO());
        
        // Lấy danh sách danh mục
        List<CategoryDTO> categories = categoryService.findAllActive();
        model.addAttribute("categories", categories);
        
        // Lấy danh sách cấu hình đang active
        List<ConfigurationsDTO> configurations = configurationsService.findAllActive();
        model.addAttribute("configurations", configurations);
        
        return "admin/products/add";
    }
    
    @PostMapping("/products/add")
    public String saveProduct(@ModelAttribute ProductDTO productDTO, 
                            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
                            @RequestParam Map<String, String> configValues,
                            RedirectAttributes redirectAttributes) {
        try {
            // Xử lý lưu sản phẩm
            ProductDTO savedProduct = productService.save(productDTO);
            Long productId = savedProduct.getId();
            
            // Xử lý lưu ảnh
            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<ProductImagesDTO> productImages = new ArrayList<>();
                String uploadDir = "src/main/resources/static/images/products/";
                
                for (MultipartFile file : imageFiles) {
                    if (!file.isEmpty()) {
                        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                        Path filePath = Paths.get(uploadDir + fileName);
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                        
                        ProductImagesDTO imageDTO = new ProductImagesDTO();
                        imageDTO.setIdProduct(productId);
                        imageDTO.setName(fileName);
                        imageDTO.setUrlImg("/static/images/products/" + fileName);
                        productImages.add(imageDTO);
                    }
                }
                
                if (!productImages.isEmpty()) {
                    // Cập nhật ảnh chính cho sản phẩm
                    productDTO.setImage(productImages.get(0).getUrlImg());
                    productService.updateMainImage(productId, productImages.get(0).getUrlImg());
                    
                    // Lưu các ảnh phụ
                    productService.saveProductImages(productImages);
                }
            }
            
            // Xử lý lưu cấu hình sản phẩm
            List<ProductConfigDTO> productConfigs = new ArrayList<>();
            System.out.println("Số lượng configValues: " + configValues.size());
            configValues.forEach((key, value) -> {
                System.out.println("Đang xử lý key: " + key + ", value: " + value);
                if (value != null && !value.trim().isEmpty()) {
                    try {
                        // Lấy ID cấu hình từ key (configValues[1] -> 1)
                        String idConfigStr = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
                        Long idConfig = Long.parseLong(idConfigStr);
                        
                        System.out.println("Đã parse được idConfig: " + idConfig);
                        
                        ProductConfigDTO configDTO = new ProductConfigDTO();
                        configDTO.setIdProduct(productId);
                        configDTO.setIdConfig(idConfig);
                        configDTO.setValue(value.trim());
                        productConfigs.add(configDTO);
                        
                        System.out.println("Đã thêm config vào list: " + configDTO);
                    } catch (Exception e) {
                        System.err.println("Lỗi xử lý key cấu hình: " + key + ", chi tiết: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            
            System.out.println("Số lượng productConfigs sau khi xử lý: " + productConfigs.size());
            if (!productConfigs.isEmpty()) {
                System.out.println("Bắt đầu lưu product configs");
                productService.saveProductConfigs(productConfigs);
                System.out.println("Đã lưu xong product configs");
            }
            
            redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
            return "redirect:/admin/products/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/admin/products/add";
        }
    }
    
    @GetMapping("/products/categories")
    public String productsCategories(Model model) {
        model.addAttribute("activeMenu", "products");
        model.addAttribute("activeSubMenu", "products-categories");
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
        redirectAttributes.addFlashAttribute("activeMenu", "products");
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
        model.addAttribute("activeMenu", "products");
        model.addAttribute("activeSubMenu", "products-config");
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
        redirectAttributes.addFlashAttribute("activeMenu", "products");
        try {
            System.out.println("Đang lưu cấu hình: " + configurationsDTO.getName());
            
            // Đặt giá trị mặc định
            if (configurationsDTO.getIsActive() == null) {
                configurationsDTO.setIsActive(true);
            }
            configurationsDTO.setIsDelete(false);
            
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

    @PostMapping("/products/config/update/{id}")
    public String updateConfiguration(@PathVariable Long id, @ModelAttribute ConfigurationsDTO configurationsDTO, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "products");
        try {
            configurationsDTO.setId(id);
            ConfigurationsDTO updatedConfig = configurationsService.save(configurationsDTO);
            System.out.println("Đã cập nhật cấu hình: " + updatedConfig.getId() + " - " + updatedConfig.getName());
            redirectAttributes.addFlashAttribute("success", "Cấu hình đã được cập nhật thành công.");
            return "redirect:/admin/products/config";
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật cấu hình: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật cấu hình: " + e.getMessage());
            return "redirect:/admin/products/config";
        }
    }

    @PostMapping("/products/config/delete/{id}")
    public String deleteConfiguration(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "products");
        try {
            configurationsService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Cấu hình đã được xóa thành công.");
            return "redirect:/admin/products/config";
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa cấu hình: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa cấu hình: " + e.getMessage());
            return "redirect:/admin/products/config";
        }
    }

    @GetMapping("/customers/list")
    public String customersList(Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String registrationDate,
            @RequestParam(required = false) String sortBy) {
        model.addAttribute("activeMenu", "customers");
        try {
            List<CustomerDTO> allCustomers;
            
            // Xử lý tìm kiếm
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                allCustomers = customerService.searchCustomers(searchTerm);
                System.out.println("Tìm kiếm khách hàng với từ khóa: " + searchTerm);
            } else {
                allCustomers = customerService.findAll();
            }
            
            // Xử lý lọc theo trạng thái
            if (status != null && !status.isEmpty()) {
                boolean isActive = "1".equals(status);
                allCustomers = allCustomers.stream()
                    .filter(c -> c.getIsActive() == isActive)
                    .collect(Collectors.toList());
            }
            
            // Xử lý lọc theo ngày đăng ký
            if (registrationDate != null && !registrationDate.isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime startDate;
                
                switch (registrationDate) {
                    case "today":
                        startDate = now.toLocalDate().atStartOfDay();
                        break;
                    case "week":
                        startDate = now.minusWeeks(1);
                        break;
                    case "month":
                        startDate = now.minusMonths(1);
                        break;
                    case "year":
                        startDate = now.minusYears(1);
                        break;
                    default:
                        startDate = null;
                }
                
                if (startDate != null) {
                    LocalDateTime finalStartDate = startDate;
                    allCustomers = allCustomers.stream()
                        .filter(c -> c.getCreatedDate() != null && c.getCreatedDate().isAfter(finalStartDate))
                        .collect(Collectors.toList());
                }
            }
            
            // Xử lý sắp xếp
            if (sortBy != null && !sortBy.isEmpty()) {
                switch (sortBy) {
                    case "newest":
                        allCustomers.sort((c1, c2) -> c2.getCreatedDate().compareTo(c1.getCreatedDate()));
                        break;
                    case "oldest":
                        allCustomers.sort((c1, c2) -> c1.getCreatedDate().compareTo(c2.getCreatedDate()));
                        break;
                    case "name_asc":
                        allCustomers.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
                        break;
                    case "name_desc":
                        allCustomers.sort((c1, c2) -> c2.getName().compareToIgnoreCase(c1.getName()));
                        break;
                }
            }
            
            System.out.println("Tổng số khách hàng sau khi lọc: " + allCustomers.size());
            
            // Tính toán phân trang
            int totalItems = allCustomers.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            page = Math.max(1, Math.min(page, totalPages));
            
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, totalItems);
            
            List<CustomerDTO> pageCustomers = fromIndex < totalItems ?
                allCustomers.subList(fromIndex, toIndex) : new ArrayList<>();
            
            // Thêm thông tin vào model
            model.addAttribute("customers", pageCustomers);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", Math.max(1, totalPages));
            model.addAttribute("pageSize", size);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("status", status);
            model.addAttribute("registrationDate", registrationDate);
            model.addAttribute("sortBy", sortBy);
            
            if (pageCustomers.isEmpty()) {
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    model.addAttribute("message", "Không tìm thấy khách hàng nào phù hợp với từ khóa: " + searchTerm);
                } else {
                    model.addAttribute("message", "Không có khách hàng nào");
                }
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi trong customersList: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách khách hàng");
            model.addAttribute("customers", new ArrayList<>());
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalItems", 0);
        }
        
        return "admin/customers/list";
    }

    @GetMapping("/customers/details/{id}")
    public String customerDetails(@PathVariable Long id, Model model) {
        model.addAttribute("activeMenu", "customers");
        try {
            CustomerDTO customer = customerService.findById(id);
            if (customer != null) {
                model.addAttribute("customer", customer);
                return "admin/customers/details";
            }
            return "redirect:/admin/customers/list?error=customer_not_found";
        } catch (Exception e) {
            return "redirect:/admin/customers/list?error=error_loading_customer";
        }
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        model.addAttribute("activeMenu", "customers");
        try {
            CustomerDTO customer = customerService.findById(id);
            if (customer != null) {
                model.addAttribute("customer", customer);
                return "admin/customers/edit";
            }
            return "redirect:/admin/customers/list?error=customer_not_found";
        } catch (Exception e) {
            return "redirect:/admin/customers/list?error=error_loading_customer";
        }
    }

    @PostMapping("/customers/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute CustomerDTO customerDTO, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "customers");
        try {
            customerDTO.setId(id);
            CustomerDTO updatedCustomer = customerService.update(customerDTO);
            if (updatedCustomer != null) {
                redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin khách hàng thành công");
                return "redirect:/admin/customers/list";
            }
            redirectAttributes.addFlashAttribute("error", "Không thể cập nhật thông tin khách hàng");
            return "redirect:/admin/customers/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật thông tin khách hàng: " + e.getMessage());
            return "redirect:/admin/customers/list";
        }
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("customerId") Long customerId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "customers");
        try {
            System.out.println("Đang xóa khách hàng có ID: " + customerId);
            boolean deleted = customerService.delete(customerId);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Xóa khách hàng thành công");
                return "redirect:/admin/customers/list";
            }
            redirectAttributes.addFlashAttribute("error", "Không thể xóa khách hàng");
            return "redirect:/admin/customers/list";
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa khách hàng: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa khách hàng: " + e.getMessage());
            return "redirect:/admin/customers/list";
        }
    }

    // URL /admin/orders/list sẽ hiển thị trang danh sách đơn hàng
    @GetMapping("/orders/list")
    public String ordersList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String timeRange,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sort,
            Model model) {
        
        // Thêm active menu
        model.addAttribute("activeMenu", "orders");
        model.addAttribute("activeSubMenu", "orders-list");
        
        try {
            List<OrdersDTO> orders = ordersService.findAll();
            
            // Xử lý tìm kiếm
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchKeyword = keyword.toLowerCase().trim();
                orders = orders.stream()
                        .filter(o -> (o.getIOrders() != null && o.getIOrders().toLowerCase().contains(searchKeyword)) ||
                                   (o.getNameReceiver() != null && o.getNameReceiver().toLowerCase().contains(searchKeyword)))
                        .collect(Collectors.toList());
                model.addAttribute("keyword", keyword);
            }
            
            // Xử lý lọc theo thời gian
            if (timeRange != null) {
                LocalDateTime now = LocalDateTime.now();
                switch (timeRange) {
                    case "today":
                        orders = orders.stream()
                                .filter(o -> o.getOrdersDate().toLocalDate().equals(now.toLocalDate()))
                                .collect(Collectors.toList());
                        break;
                    case "yesterday":
                        orders = orders.stream()
                                .filter(o -> o.getOrdersDate().toLocalDate().equals(now.minusDays(1).toLocalDate()))
                                .collect(Collectors.toList());
                        break;
                    case "thisweek":
                        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                        orders = orders.stream()
                                .filter(o -> !o.getOrdersDate().isBefore(startOfWeek))
                                .collect(Collectors.toList());
                        break;
                    case "thismonth":
                        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
                        orders = orders.stream()
                                .filter(o -> !o.getOrdersDate().isBefore(startOfMonth))
                                .collect(Collectors.toList());
                        break;
                    case "lastmonth":
                        LocalDateTime startOfLastMonth = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
                        LocalDateTime endOfLastMonth = startOfLastMonth.with(TemporalAdjusters.lastDayOfMonth());
                        orders = orders.stream()
                                .filter(o -> !o.getOrdersDate().isBefore(startOfLastMonth) && !o.getOrdersDate().isAfter(endOfLastMonth))
                                .collect(Collectors.toList());
                        break;
                }
            }
            
            // Xử lý lọc theo trạng thái
            if (status != null) {
                switch (status) {
                    case "pending":
                        orders = orders.stream().filter(o -> o.getStatus() == 0).collect(Collectors.toList());
                        break;
                    case "confirmed":
                        orders = orders.stream().filter(o -> o.getStatus() == 1).collect(Collectors.toList());
                        break;
                    case "shipping":
                        orders = orders.stream().filter(o -> o.getStatus() == 2).collect(Collectors.toList());
                        break;
                    case "completed":
                        orders = orders.stream().filter(o -> o.getStatus() == 3).collect(Collectors.toList());
                        break;
                    case "cancelled":
                        orders = orders.stream().filter(o -> o.getStatus() == 4).collect(Collectors.toList());
                        break;
                }
            }
            
            // Xử lý sắp xếp
            if (sort != null) {
                switch (sort) {
                    case "newest":
                        orders.sort((a, b) -> b.getOrdersDate().compareTo(a.getOrdersDate()));
                        break;
                    case "oldest":
                        orders.sort((a, b) -> a.getOrdersDate().compareTo(b.getOrdersDate()));
                        break;
                    case "total_asc":
                        orders.sort((a, b) -> a.getTotalMoney().compareTo(b.getTotalMoney()));
                        break;
                    case "total_desc":
                        orders.sort((a, b) -> b.getTotalMoney().compareTo(a.getTotalMoney()));
                        break;
                }
            }

            model.addAttribute("orders", orders);
            model.addAttribute("selectedTimeRange", timeRange);
            model.addAttribute("selectedStatus", status);
            model.addAttribute("selectedSort", sort);
            
            return "admin/orders/list";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách đơn hàng: " + e.getMessage());
            return "admin/orders/list";
        }
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
        boolean deleted = ordersService.delete(id);
        
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Xóa đơn hàng thành công");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa đơn hàng");
        }
        
        return "redirect:/admin/orders/list";
    }
    
    @GetMapping("/orders/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        model.addAttribute("activeMenu", "orders");
        OrdersDTO order = ordersService.findById(id);
        if (order == null) {
            return "redirect:/admin/orders";
        }
        
        model.addAttribute("order", order);
        return "admin/orders/details";
    }
    
    @PostMapping("/orders/{id}/update-status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam Integer status, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
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

    @GetMapping("/orders/transport")
    public String transportMethods(Model model) {
        model.addAttribute("activeMenu", "orders");
        model.addAttribute("activeSubMenu", "orders-transport");
        try {
            List<TransportMethodDTO> transportMethods = transportMethodService.findAll();
            model.addAttribute("transportMethods", transportMethods);
            model.addAttribute("newTransportMethod", new TransportMethodDTO());
            
            if (transportMethods.isEmpty()) {
                model.addAttribute("message", "Không có phương thức vận chuyển nào");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách phương thức vận chuyển: " + e.getMessage());
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách phương thức vận chuyển");
        }
        return "admin/orders/transport";
    }

    @PostMapping("/orders/transport/add")
    public String addTransportMethod(@ModelAttribute TransportMethodDTO transportMethodDTO, 
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
        try {
            // Thiết lập các giá trị mặc định
            transportMethodDTO.setIsActive(true);
            transportMethodDTO.setIsDelete(false);
            transportMethodDTO.setCreatedDate(LocalDateTime.now());
            
            TransportMethodDTO savedMethod = transportMethodService.save(transportMethodDTO);
            redirectAttributes.addFlashAttribute("success", 
                "Thêm phương thức vận chuyển " + savedMethod.getName() + " thành công");
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm phương thức vận chuyển: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                "Có lỗi xảy ra khi thêm phương thức vận chuyển: " + e.getMessage());
        }
        return "redirect:/admin/orders/transport";
    }

    @PostMapping("/orders/transport/update/{id}")
    public String updateTransportMethod(@PathVariable Long id, 
                                      @ModelAttribute TransportMethodDTO transportMethodDTO,
                                      RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
        try {
            transportMethodDTO.setId(id);
            transportMethodDTO.setUpdatedDate(LocalDateTime.now());
            
            TransportMethodDTO updatedMethod = transportMethodService.update(transportMethodDTO);
            redirectAttributes.addFlashAttribute("success", 
                "Cập nhật phương thức vận chuyển " + updatedMethod.getName() + " thành công");
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật phương thức vận chuyển: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                "Có lỗi xảy ra khi cập nhật phương thức vận chuyển: " + e.getMessage());
        }
        return "redirect:/admin/orders/transport";
    }

    @PostMapping("/orders/transport/delete/{id}")
    public String deleteTransportMethod(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
        try {
            boolean deleted = transportMethodService.delete(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Xóa phương thức vận chuyển thành công");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa phương thức vận chuyển");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa phương thức vận chuyển: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                "Có lỗi xảy ra khi xóa phương thức vận chuyển: " + e.getMessage());
        }
        return "redirect:/admin/orders/transport";
    }

    @GetMapping("/orders/payment")
    public String showPaymentMethods(Model model) {
        model.addAttribute("activeMenu", "orders");
        model.addAttribute("activeSubMenu", "orders-payment");
        List<PaymentMethodDTO> paymentMethods = paymentMethodService.findAll();
        model.addAttribute("paymentMethods", paymentMethods);
        return "admin/orders/payment";
    }

    @PostMapping("/orders/payment/add")
    public String addPaymentMethod(@ModelAttribute PaymentMethodDTO paymentMethodDTO, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
        try {
            paymentMethodService.save(paymentMethodDTO);
            redirectAttributes.addFlashAttribute("success", "Thêm phương thức thanh toán thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm phương thức thanh toán");
        }
        return "redirect:/admin/orders/payment";
    }

    @PostMapping("/orders/payment/update/{id}")
    public String updatePaymentMethod(@PathVariable Long id, @ModelAttribute PaymentMethodDTO paymentMethodDTO, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
        try {
            paymentMethodService.update(id, paymentMethodDTO);
            redirectAttributes.addFlashAttribute("success", "Cập nhật phương thức thanh toán thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật phương thức thanh toán");
        }
        return "redirect:/admin/orders/payment";
    }

    @GetMapping("/orders/payment/delete/{id}")
    public String deletePaymentMethod(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "orders");
        try {
            paymentMethodService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa phương thức thanh toán thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa phương thức thanh toán");
        }
        return "redirect:/admin/orders/payment";
    }

    // HIỂN THỊ DANH SÁCH TIN TỨC
    @GetMapping("/news")
    public String newsManagement(Model model) {
        model.addAttribute("activeMenu", "news");
        model.addAttribute("activeSubMenu", "news-list");
        List<NewsDTO> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        model.addAttribute("newNews", new NewsDTO()); // Form thêm mới
        model.addAttribute("newsCategories", newsCategoryService.findAll()); // Thêm dòng này
        return "admin/news/list";
    }

    // THÊM TIN TỨC MỚI
    @PostMapping("/news/add")
    public String addNews(@ModelAttribute("newNews") @Valid NewsDTO newsDTO,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "news");
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ");
            return "redirect:/admin/news";
        }
        
        try {
            newsService.save(newsDTO);
            redirectAttributes.addFlashAttribute("success", "Thêm tin tức thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm tin tức: " + e.getMessage());
        }
        return "redirect:/admin/news";
    }

    // CẬP NHẬT TIN TỨC
    @PostMapping("/news/update/{id}")
    public String updateNews(@PathVariable Long id,
                            @ModelAttribute @Valid NewsDTO newsDTO,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "news");
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu cập nhật không hợp lệ");
            return "redirect:/admin/news";
        }

        try {
            newsService.update(id, newsDTO);
            redirectAttributes.addFlashAttribute("success", "Cập nhật tin tức thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật tin tức: " + e.getMessage());
        }
        return "redirect:/admin/news";
    }

    // XÓA TIN TỨC
    @GetMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable Long id,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeMenu", "news");
        try {
            newsService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa tin tức thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa tin tức: " + e.getMessage());
        }
        return "redirect:/admin/news";
    }


    // HIỂN THỊ DANH SÁCH DANH MỤC TIN TỨC
@GetMapping("/news/categories")
public String listNewsCategories(Model model) {
    model.addAttribute("activeMenu", "news");
    model.addAttribute("activeSubMenu", "news-categories");
    model.addAttribute("categories", newsCategoryService.findAll());
    model.addAttribute("newCategory", new NewsCategoryDTO());
    return "admin/news/categories/list";
}

// THÊM DANH MỤC TIN TỨC MỚI
@PostMapping("/news/categories/add")
public String addNewsCategory(@ModelAttribute("newCategory") NewsCategoryDTO dto,
                              RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("activeMenu", "news");
    try {
        newsCategoryService.save(dto);
        redirectAttributes.addFlashAttribute("success", "Thêm danh mục thành công");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm danh mục: " + e.getMessage());
    }
    return "redirect:/admin/news/categories";
}

// HIỂN THỊ FORM SỬA DANH MỤC
@GetMapping("/news/categories/edit/{id}")
public String editNewsCategoryForm(@PathVariable Long id, Model model) {
    model.addAttribute("activeMenu", "news");
    NewsCategoryDTO dto = newsCategoryService.findById(id);
    model.addAttribute("category", dto);
    return "admin/news/categories/edit";
}

// CẬP NHẬT DANH MỤC TIN TỨC
@PostMapping("/news/categories/update/{id}")
public String updateNewsCategory(@PathVariable Long id, @ModelAttribute NewsCategoryDTO dto,
                                 RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("activeMenu", "news");
    try {
        newsCategoryService.update(id, dto);
        redirectAttributes.addFlashAttribute("success", "Cập nhật danh mục thành công");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
    }
    return "redirect:/admin/news/categories";
}

// XÓA DANH MỤC TIN TỨC
@GetMapping("/news/categories/delete/{id}")
public String deleteNewsCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("activeMenu", "news");
    try {
        newsCategoryService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Xóa danh mục thành công");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa: " + e.getMessage());
    }
    return "redirect:/admin/news/categories";
}

@GetMapping("/products/view/{id}")
public String viewProduct(@PathVariable Long id, Model model) {
    model.addAttribute("activeMenu", "products");
    try {
        ProductDTO product = productService.findById(id);
        if (product == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + id);
        }
        model.addAttribute("product", product);
        return "admin/products/view";
    } catch (Exception e) {
        model.addAttribute("error", "Có lỗi xảy ra khi tải thông tin sản phẩm: " + e.getMessage());
        return "redirect:/admin/products/list";
    }
}

@GetMapping("/products/edit/{id}")
public String editProduct(@PathVariable Long id, Model model) {
    model.addAttribute("activeMenu", "products");
    try {
        ProductDTO product = productService.findById(id);
        if (product == null) {
            model.addAttribute("error", "Không tìm thấy sản phẩm với ID: " + id);
            return "admin/products/list";
        }

        // Khởi tạo các danh sách rỗng nếu null
        if (product.getProductImages() == null) {
            product.setProductImages(new ArrayList<>());
        }
        if (product.getProductConfigs() == null) {
            product.setProductConfigs(new ArrayList<>());
        }
        if (product.getCategoryName() == null) {
            product.setCategoryName("");
        }
        
        // Lấy danh sách danh mục để hiển thị trong dropdown
        List<CategoryDTO> categories = categoryService.findAllActive();
        // Lấy danh sách cấu hình để hiển thị trong form
        List<ConfigurationsDTO> configurations = configurationsService.findAllActive();
        
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("configurations", configurations);
        
        return "admin/products/edit";
    } catch (Exception e) {
        e.printStackTrace();
        // Thử lấy lại danh sách cơ bản nếu có lỗi
        try {
            List<CategoryDTO> categories = categoryService.findAllActive();
            List<ConfigurationsDTO> configurations = configurationsService.findAllActive();
            model.addAttribute("categories", categories);
            model.addAttribute("configurations", configurations);
        } catch (Exception ex) {
            // Bỏ qua nếu không lấy được danh sách
        }
        
        model.addAttribute("error", "Có lỗi xảy ra khi tải thông tin sản phẩm: " + e.getMessage());
        return "admin/products/edit"; // Vẫn ở lại trang edit thay vì redirect
    }
}

@PostMapping("/products/edit/{id}")
public String updateProduct(@PathVariable Long id, @ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes) {
    try {
        ProductDTO existingProduct = productService.findById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + id);
        }
        
        // Cập nhật thông tin cơ bản
        productDTO.setId(id);
        productDTO.setUpdatedDate(LocalDateTime.now());
        productDTO.setUpdatedBy(1L);
        productDTO.setIsDelete(false); // Đảm bảo isDelete luôn là false
        
        // Giữ lại các giá trị từ sản phẩm cũ
        productDTO.setCreatedBy(existingProduct.getCreatedBy());
        productDTO.setCreatedDate(existingProduct.getCreatedDate());
        
        // Giữ lại ảnh cũ nếu không có ảnh mới
        if (productDTO.getImageFiles() == null || productDTO.getImageFiles().isEmpty() 
            || productDTO.getImageFiles().get(0).isEmpty()) {
            productDTO.setImage(existingProduct.getImage());
            productDTO.setProductImages(existingProduct.getProductImages());
        } else {
            // Xử lý file upload nếu có
            List<ProductImagesDTO> productImages = new ArrayList<>();
            
            // Tạo thư mục nếu chưa tồn tại
            String uploadDir = "src/main/resources/static/images/products/";
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Xử lý từng file
            for (MultipartFile file : productDTO.getImageFiles()) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    String cleanedFilename = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
                    String newFileName = System.currentTimeMillis() + "_" + cleanedFilename;
                    
                    Path filePath = uploadPath.resolve(newFileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    
                    String imageUrl = "/static/images/products/" + newFileName;
                    
                    ProductImagesDTO imageDTO = new ProductImagesDTO();
                    imageDTO.setName(originalFilename);
                    imageDTO.setUrlImg(imageUrl);
                    productImages.add(imageDTO);
                    
                    if (productDTO.getImage() == null) {
                        productDTO.setImage(imageUrl);
                    }
                }
            }
            
            if (!productImages.isEmpty()) {
                productDTO.setProductImages(productImages);
            }
        }
        
        ProductDTO updatedProduct = productService.save(productDTO);
        redirectAttributes.addFlashAttribute("success", "Sản phẩm " + updatedProduct.getName() + " đã được cập nhật thành công");
        return "redirect:/admin/products/list";
    } catch (Exception e) {
        System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm: " + e.getMessage());
        return "redirect:/admin/products/edit/" + id;
    }
}

@PostMapping("/products/delete/{id}")
public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
        ProductDTO product = productService.findById(id);
        if (product == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + id);
        }
        
        // Xóa sản phẩm
        productService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được xóa thành công");
        return "redirect:/admin/products/list";
    } catch (Exception e) {
        System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa sản phẩm: " + e.getMessage());
        return "redirect:/admin/products/list";
    }
}

} 