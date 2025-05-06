package com.example.project_shoes.service;

import com.example.project_shoes.dto.PaymentMethodDTO;
import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodDTO> findAll();
    PaymentMethodDTO findById(Long id);
    PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO);
    PaymentMethodDTO update(Long id, PaymentMethodDTO paymentMethodDTO);
    void delete(Long id);
} 