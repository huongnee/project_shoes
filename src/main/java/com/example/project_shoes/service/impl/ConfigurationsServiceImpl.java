package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.ConfigurationsDTO;
import com.example.project_shoes.entity.Configurations;
import com.example.project_shoes.mapper.ConfigurationsMapper;
import com.example.project_shoes.repository.ConfigurationsRepository;
import com.example.project_shoes.service.ConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationsServiceImpl implements ConfigurationsService {
    
    @Autowired
    private ConfigurationsRepository configurationsRepository;
    
    @Autowired
    private ConfigurationsMapper configurationsMapper;

    @Override
    public List<ConfigurationsDTO> findAll() {
        List<Configurations> configurations = configurationsRepository.findByIsDeleteFalse();
        return configurationsMapper.toDTOList(configurations);
    }

    @Override
    public ConfigurationsDTO findById(Long id) {
        Configurations configurations = configurationsRepository.findByIdAndIsDeleteFalse(id);
        return configurationsMapper.toDTO(configurations);
    }

    @Override
    public ConfigurationsDTO save(ConfigurationsDTO configurationsDTO) {
        Configurations configurations = configurationsMapper.toEntity(configurationsDTO);
        configurations.setIsDelete(false);
        Configurations savedConfigurations = configurationsRepository.save(configurations);
        return configurationsMapper.toDTO(savedConfigurations);
    }

    @Override
    public ConfigurationsDTO update(ConfigurationsDTO configurationsDTO) {
        Configurations existingConfigurations = configurationsRepository.findByIdAndIsDeleteFalse(configurationsDTO.getId());
        if (existingConfigurations != null) {
            Configurations configurations = configurationsMapper.toEntity(configurationsDTO);
            configurations.setIsDelete(false);
            Configurations updatedConfigurations = configurationsRepository.save(configurations);
            return configurationsMapper.toDTO(updatedConfigurations);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Configurations existingConfigurations = configurationsRepository.findByIdAndIsDeleteFalse(id);
        if (existingConfigurations != null) {
            existingConfigurations.setIsDelete(true);
            configurationsRepository.save(existingConfigurations);
            return true;
        }
        return false;
    }

    @Override
    public List<ConfigurationsDTO> findByName(String name) {
        List<Configurations> configurations = configurationsRepository.findByNameContainingAndIsDeleteFalse(name);
        return configurationsMapper.toDTOList(configurations);
    }
} 