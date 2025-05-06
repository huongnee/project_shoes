package com.example.project_shoes.service;

import com.example.project_shoes.dto.TransportMethodDTO;
import java.util.List;

public interface TransportMethodService {
    List<TransportMethodDTO> findAll();
    TransportMethodDTO findById(Long id);
    TransportMethodDTO save(TransportMethodDTO transportMethodDTO);
    TransportMethodDTO update(TransportMethodDTO transportMethodDTO);
    boolean delete(Long id);
} 