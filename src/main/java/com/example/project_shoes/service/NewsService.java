package com.example.project_shoes.service;

import com.example.project_shoes.dto.NewsDTO;
import java.util.List;

public interface NewsService {
    List<NewsDTO> getAllNews();
    NewsDTO getNewsById(Long id);
    List<NewsDTO> getNewsByCategory(Long categoryId);
    void save(NewsDTO newsDTO);
    void update(Long id, NewsDTO newsDTO);
    void delete(Long id);
}
