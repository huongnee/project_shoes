package com.example.project_shoes.service;

import com.example.project_shoes.entity.PaymentMethod;
import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> findAllActive();
    PaymentMethod findById(Long id);
} 