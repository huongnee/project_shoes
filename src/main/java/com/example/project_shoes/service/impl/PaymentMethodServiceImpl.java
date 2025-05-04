package com.example.project_shoes.service.impl;

import com.example.project_shoes.entity.PaymentMethod;
import com.example.project_shoes.repository.PaymentMethodRepository;
import com.example.project_shoes.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> findAllActive() {
        return paymentMethodRepository.findByIsDeleteFalse();
    }

    @Override
    public PaymentMethod findById(Long id) {
        return paymentMethodRepository.findByIdAndIsDeleteFalse(id);
    }
} 