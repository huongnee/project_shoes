package com.example.project_shoes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index() {
        return "admin/index";
    }

    @GetMapping({"/products", "/products/list", "/products/list.html"})
    public String productsList() {
        return "admin/products/list";
    }

    @GetMapping("/orders/list")
    public String ordersList() {
        return "admin/orders/list";
    }

    @GetMapping("/orders/detail")
    public String ordersDetail() {
        return "admin/orders/detail";
    }
} 