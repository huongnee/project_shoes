package com.example.project_shoes.service.impl;

import com.example.project_shoes.entity.TransportMethod;
import com.example.project_shoes.repository.TransportMethodRepository;
import com.example.project_shoes.service.TransportMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportMethodServiceImpl implements TransportMethodService {

    @Autowired
    private TransportMethodRepository transportMethodRepository;

    @Override
    public List<TransportMethod> findAllActive() {
        return transportMethodRepository.findByIsDeleteFalse();
    }

    @Override
    public TransportMethod findById(Long id) {
        return transportMethodRepository.findByIdAndIsDeleteFalse(id);
    }
} 