package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.TransportMethodDTO;
import com.example.project_shoes.entity.TransportMethod;
import com.example.project_shoes.repository.TransportMethodRepository;
import com.example.project_shoes.service.TransportMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportMethodServiceImpl implements TransportMethodService {

    @Autowired
    private TransportMethodRepository transportMethodRepository;

    @Override
    public List<TransportMethodDTO> findAll() {
        return transportMethodRepository.findByIsDeleteFalse().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransportMethodDTO findById(Long id) {
        return transportMethodRepository.findByIdAndIsDeleteFalse(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public TransportMethodDTO save(TransportMethodDTO transportMethodDTO) {
        TransportMethod transportMethod = convertToEntity(transportMethodDTO);
        transportMethod = transportMethodRepository.save(transportMethod);
        return convertToDTO(transportMethod);
    }

    @Override
    public TransportMethodDTO update(TransportMethodDTO transportMethodDTO) {
        TransportMethod existingMethod = transportMethodRepository.findByIdAndIsDeleteFalse(transportMethodDTO.getId())
                .orElse(null);
        if (existingMethod == null) {
            return null;
        }
        
        // Cập nhật thông tin
        existingMethod.setName(transportMethodDTO.getName());
        existingMethod.setNotes(transportMethodDTO.getNotes());
        existingMethod.setIsActive(transportMethodDTO.getIsActive());
        existingMethod.setUpdatedDate(transportMethodDTO.getUpdatedDate());
        
        existingMethod = transportMethodRepository.save(existingMethod);
        return convertToDTO(existingMethod);
    }

    @Override
    public boolean delete(Long id) {
        TransportMethod transportMethod = transportMethodRepository.findByIdAndIsDeleteFalse(id)
                .orElse(null);
        if (transportMethod == null) {
            return false;
        }
        
        transportMethod.setIsDelete(true);
        transportMethodRepository.save(transportMethod);
        return true;
    }

    private TransportMethodDTO convertToDTO(TransportMethod transportMethod) {
        TransportMethodDTO dto = new TransportMethodDTO();
        dto.setId(transportMethod.getId());
        dto.setName(transportMethod.getName());
        dto.setNotes(transportMethod.getNotes());
        dto.setIsActive(transportMethod.getIsActive());
        dto.setIsDelete(transportMethod.getIsDelete());
        dto.setCreatedDate(transportMethod.getCreatedDate());
        dto.setUpdatedDate(transportMethod.getUpdatedDate());
        return dto;
    }

    private TransportMethod convertToEntity(TransportMethodDTO dto) {
        TransportMethod entity = new TransportMethod();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDelete(dto.getIsDelete());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedDate(dto.getUpdatedDate());
        return entity;
    }
} 