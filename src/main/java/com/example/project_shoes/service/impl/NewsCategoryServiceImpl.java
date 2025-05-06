package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.NewsCategoryDTO;
import com.example.project_shoes.entity.NewsCategory;
import com.example.project_shoes.mapper.NewsCategoryMapper;
import com.example.project_shoes.repository.NewsCategoryRepository;
import com.example.project_shoes.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Autowired
    private NewsCategoryRepository newsCategoryRepository;

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

    @Override
    public List<NewsCategoryDTO> findAll() {
        return newsCategoryMapper.toDTOList(newsCategoryRepository.findByIsDeleteFalse());
    }

    // @Override
    // public NewsCategoryDTO findById(Long id) {
    //     NewsCategory entity = newsCategoryRepository.findByIdAndIsDeleteFalse(id);
    //     return newsCategoryMapper.toDTO(entity);
    // }
    @Override
    public NewsCategoryDTO findById(Long id) {
    NewsCategory entity = newsCategoryRepository.findById(id) // Bỏ điều kiện isDelete = false
        .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
    return newsCategoryMapper.toDTO(entity);
}


    @Override
    public void save(NewsCategoryDTO dto) {
        NewsCategory entity = newsCategoryMapper.toEntity(dto);
        entity.setIsDelete(false);
        entity.setCreatedDate(LocalDateTime.now());
        newsCategoryRepository.save(entity);
    }

    @Override
    public void update(Long id, NewsCategoryDTO dto) {
        NewsCategory existing = newsCategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        newsCategoryMapper.updateEntityFromDTO(dto, existing);
        existing.setUpdatedDate(LocalDateTime.now());
        newsCategoryRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        NewsCategory entity = newsCategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        entity.setIsDelete(true);
        newsCategoryRepository.save(entity);
    }
    
}
