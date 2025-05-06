package com.example.project_shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDTO {
    // Thống kê tổng quan
    private long totalProducts;
    private long totalOrders;
    private long totalCustomers;
    private BigDecimal totalRevenue;
    
    // Thống kê đơn hàng theo trạng thái
    private long pendingOrders;
    private long processingOrders;
    private long shippingOrders;
    private long completedOrders;
    private long cancelledOrders;
    
    // Dữ liệu biểu đồ doanh thu
    private List<String> revenueLabels; // Nhãn thời gian (ngày, tháng)
    private List<BigDecimal> revenueData; // Dữ liệu doanh thu
    
    // Dữ liệu biểu đồ sản phẩm
    private List<String> productLabels; // Tên danh mục sản phẩm
    private List<Long> productData; // Số lượng sản phẩm
    
    // Đơn hàng gần đây
    private List<OrdersDTO> recentOrders;
}
