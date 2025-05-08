package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.ConfigurationsDTO;
import com.example.project_shoes.entity.Configurations;
import com.example.project_shoes.mapper.ConfigurationsMapper;
import com.example.project_shoes.repository.ConfigurationsRepository;
import com.example.project_shoes.service.ConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        // Đảm bảo các giá trị boolean không bị null
        if (configurations.getIsDelete() == null) {
            configurations.setIsDelete(false);
        }
        if (configurations.getIsActive() == null) {
            configurations.setIsActive(true);
        }
        Configurations savedConfigurations = configurationsRepository.save(configurations);
        return configurationsMapper.toDTO(savedConfigurations);
    }

    @Override
    public ConfigurationsDTO update(ConfigurationsDTO configurationsDTO) {
        Configurations existingConfigurations = configurationsRepository.findByIdAndIsDeleteFalse(configurationsDTO.getId());
        if (existingConfigurations != null) {
            Configurations configurations = configurationsMapper.toEntity(configurationsDTO);
            // Đảm bảo các giá trị boolean không bị null
            if (configurations.getIsDelete() == null) {
                configurations.setIsDelete(false);
            }
            if (configurations.getIsActive() == null) {
                configurations.setIsActive(true);
            }
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
    
    @Override
    public List<ConfigurationsDTO> findAllActive() {
        try {
            // Sử dụng native query
            List<Configurations> configs = configurationsRepository.findAllNotDeleted();
            if (!configs.isEmpty()) {
                System.out.println("Native query tìm thấy: " + configs.size() + " cấu hình");
                return mapToConfigurationsDTOList(configs);
            }
            
            // Sử dụng JPQL
            List<Configurations> activeConfigs = configurationsRepository.findByIsActiveTrueAndIsDeleteFalse();
            if (!activeConfigs.isEmpty()) {
                System.out.println("JPQL tìm thấy: " + activeConfigs.size() + " cấu hình");
                return mapToConfigurationsDTOList(activeConfigs);
            }
            
            // Sử dụng findAll
            List<Configurations> allConfigs = configurationsRepository.findAll();
            if (!allConfigs.isEmpty()) {
                System.out.println("findAll tìm thấy: " + allConfigs.size() + " cấu hình");
                // Lọc thủ công để đảm bảo chỉ lấy các cấu hình active và không bị xóa
                List<Configurations> filteredConfigs = allConfigs.stream()
                    .filter(c -> (c.getIsActive() == null || c.getIsActive()) && 
                                (c.getIsDelete() == null || !c.getIsDelete()))
                    .collect(Collectors.toList());
                System.out.println("Sau khi lọc: " + filteredConfigs.size() + " cấu hình");
                return mapToConfigurationsDTOList(filteredConfigs);
            }
            
            System.out.println("Không tìm thấy cấu hình nào");
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm cấu hình: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    private ConfigurationsDTO mapToConfigurationsDTO(Configurations config) {
        ConfigurationsDTO dto = new ConfigurationsDTO();
        dto.setId(config.getId());
        dto.setName(config.getName());
        dto.setNotes(config.getNotes());
        // Đảm bảo các giá trị boolean không bị null
        dto.setIsActive(config.getIsActive() == null ? true : config.getIsActive());
        dto.setIsDelete(config.getIsDelete() == null ? false : config.getIsDelete());
        return dto;
    }
    
    private List<ConfigurationsDTO> mapToConfigurationsDTOList(List<Configurations> configs) {
        return configs.stream()
                .map(this::mapToConfigurationsDTO)
                .collect(Collectors.toList());
    }
} 