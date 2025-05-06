package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.NewsDTO;
import com.example.project_shoes.entity.News;
import com.example.project_shoes.mapper.NewsMapper;
import com.example.project_shoes.repository.NewsRepository;
import com.example.project_shoes.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime; // Thêm dòng này[1]
import com.example.project_shoes.mapper.NewsMapper;


import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<NewsDTO> getAllNews() {
        List<News> newsList = newsRepository.findByIsDeleteFalse();
        return newsMapper.toDTOList(newsList);
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        News news = newsRepository.findByIdAndIsDeleteFalse(id);
        return newsMapper.toDTO(news);
    }

    @Override
    public List<NewsDTO> getNewsByCategory(Long categoryId) {
        List<News> newsList = newsRepository.findByIdNewsCategoryAndIsDeleteFalse(categoryId);
        return newsMapper.toDTOList(newsList);
    }

    @Override
    public void save(NewsDTO newsDTO) {
        News news = newsMapper.toEntity(newsDTO);
        news.setIsDelete(false);
        news.setCreatedDate(LocalDateTime.now());
        newsRepository.save(news);
    }

    @Override
    public void update(Long id, NewsDTO newsDTO) {
        News existingNews = newsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức"));
        
        newsMapper.updateEntityFromDTO(newsDTO, existingNews);
        existingNews.setUpdatedDate(LocalDateTime.now());
        newsRepository.save(existingNews);
    }

    @Override
    public void delete(Long id) {
        News news = newsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức"));
        news.setIsDelete(true);
        newsRepository.save(news);
    }
}
