package com.example.project_shoes.service;

import com.example.project_shoes.dto.NewsCategoryDTO;
import java.util.List;

public interface NewsCategoryService {
    List<NewsCategoryDTO> findAll();
    void save(NewsCategoryDTO newsCategoryDTO);
    void update(Long id, NewsCategoryDTO newsCategoryDTO);
    void delete(Long id);
    NewsCategoryDTO findById(Long id);
}
