package com.example.project_shoes.service;

import com.example.project_shoes.dto.ConfigurationsDTO;
import java.util.List;

public interface ConfigurationsService {
    List<ConfigurationsDTO> findAll();
    ConfigurationsDTO findById(Long id);
    ConfigurationsDTO save(ConfigurationsDTO configurationsDTO);
    ConfigurationsDTO update(ConfigurationsDTO configurationsDTO);
    boolean delete(Long id);
    List<ConfigurationsDTO> findByName(String name);
    
    List<ConfigurationsDTO> findAllActive();
} 