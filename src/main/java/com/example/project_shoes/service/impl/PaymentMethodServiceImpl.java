package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.PaymentMethodDTO;
import com.example.project_shoes.entity.PaymentMethod;
import com.example.project_shoes.repository.PaymentMethodRepository;
import com.example.project_shoes.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodDTO> findAll() {
        return paymentMethodRepository.findByIsDeleteFalse()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethodDTO findById(Long id) {
        return paymentMethodRepository.findByIdAndIsDeleteFalse(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(paymentMethodDTO.getName());
        paymentMethod.setNotes(paymentMethodDTO.getNotes());
        paymentMethod.setIsActive(true);
        paymentMethod.setIsDelete(false);
        paymentMethod.setCreatedDate(LocalDateTime.now());
        
        return convertToDTO(paymentMethodRepository.save(paymentMethod));
    }

    @Override
    public PaymentMethodDTO update(Long id, PaymentMethodDTO paymentMethodDTO) {
        return paymentMethodRepository.findByIdAndIsDeleteFalse(id)
                .map(paymentMethod -> {
                    paymentMethod.setName(paymentMethodDTO.getName());
                    paymentMethod.setNotes(paymentMethodDTO.getNotes());
                    paymentMethod.setIsActive(paymentMethodDTO.getIsActive());
                    paymentMethod.setUpdatedDate(LocalDateTime.now());
                    return convertToDTO(paymentMethodRepository.save(paymentMethod));
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        paymentMethodRepository.findByIdAndIsDeleteFalse(id)
                .ifPresent(paymentMethod -> {
                    paymentMethod.setIsDelete(true);
                    paymentMethod.setUpdatedDate(LocalDateTime.now());
                    paymentMethodRepository.save(paymentMethod);
                });
    }

    private PaymentMethodDTO convertToDTO(PaymentMethod paymentMethod) {
        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setId(paymentMethod.getId());
        dto.setName(paymentMethod.getName());
        dto.setNotes(paymentMethod.getNotes());
        dto.setIsActive(paymentMethod.getIsActive());
        dto.setIsDelete(paymentMethod.getIsDelete());
        dto.setCreatedDate(paymentMethod.getCreatedDate());
        dto.setUpdatedDate(paymentMethod.getUpdatedDate());
        return dto;
    }
} 