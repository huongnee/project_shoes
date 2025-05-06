package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.DashboardStatsDTO;
import com.example.project_shoes.dto.OrdersDTO;
import com.example.project_shoes.entity.Category;
import com.example.project_shoes.entity.Orders;
import com.example.project_shoes.entity.Product;
import com.example.project_shoes.repository.CategoryRepository;
import com.example.project_shoes.repository.CustomerRepository;
import com.example.project_shoes.repository.OrdersRepository;
import com.example.project_shoes.repository.ProductRepository;
import com.example.project_shoes.service.DashboardService;
import com.example.project_shoes.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private OrdersService ordersService;

    @Override
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        // Tổng số sản phẩm không bị xóa
        stats.setTotalProducts(productRepository.countByIsDeleteFalse());
        
        // Tổng số đơn hàng không bị xóa
        stats.setTotalOrders(ordersRepository.countByIsDeleteFalse());
        
        // Tổng số khách hàng không bị xóa
        stats.setTotalCustomers(customerRepository.countByIsDeleteFalse());
        
        // Tổng doanh thu từ đơn hàng hoàn thành
        BigDecimal totalRevenue = ordersRepository.sumTotalPriceByStatusAndIsDeleteFalse(3);
        stats.setTotalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
        
        // Thống kê đơn hàng theo trạng thái
        stats.setPendingOrders(ordersRepository.countByStatusAndIsDeleteFalse(0));
        stats.setProcessingOrders(ordersRepository.countByStatusAndIsDeleteFalse(1));
        stats.setShippingOrders(ordersRepository.countByStatusAndIsDeleteFalse(2));
        stats.setCompletedOrders(ordersRepository.countByStatusAndIsDeleteFalse(3));
        stats.setCancelledOrders(ordersRepository.countByStatusAndIsDeleteFalse(4));
        
        // Dữ liệu biểu đồ doanh thu trong 7 ngày gần nhất
        LocalDate today = LocalDate.now();
        List<String> revenueLabels = new ArrayList<>();
        List<BigDecimal> revenueData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1);
            
            BigDecimal dailyRevenue = ordersRepository.sumTotalPriceByOrdersDateBetweenAndStatusAndIsDeleteFalse(
                    startOfDay, endOfDay, 3);
            
            revenueLabels.add(date.format(formatter));
            revenueData.add(dailyRevenue != null ? dailyRevenue : BigDecimal.ZERO);
        }
        
        stats.setRevenueLabels(revenueLabels);
        stats.setRevenueData(revenueData);
        
        // Dữ liệu biểu đồ sản phẩm theo danh mục
        List<Category> categories = categoryRepository.findByIsDeleteFalse();
        List<String> productLabels = new ArrayList<>();
        List<Long> productData = new ArrayList<>();
        
        for (Category category : categories) {
            long productCount = productRepository.countByCategoryIdAndIsDeleteFalse(category.getId());
            if (productCount > 0) {
                productLabels.add(category.getName());
                productData.add(productCount);
            }
        }
        
        stats.setProductLabels(productLabels);
        stats.setProductData(productData);
        
        // Đơn hàng gần đây (5 đơn hàng)
        List<OrdersDTO> recentOrders = ordersService.findRecentOrders(5);
        stats.setRecentOrders(recentOrders);
        
        return stats;
    }
}
