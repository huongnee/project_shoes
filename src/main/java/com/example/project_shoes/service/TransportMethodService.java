package com.example.project_shoes.service;

import com.example.project_shoes.entity.TransportMethod;
import java.util.List;

public interface TransportMethodService {
    List<TransportMethod> findAllActive();
    TransportMethod findById(Long id);
} 