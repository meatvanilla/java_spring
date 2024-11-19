package com.example.mod4.service;

import com.example.mod4.dto.NewsDTO;
import com.example.mod4.entity.News;
import com.example.mod4.exception.NewsNotFoundException;
import com.example.mod4.mapper.NewsMapper;
import com.example.mod4.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    // Создание новости
    public NewsDTO createNews(NewsDTO newsDTO) {
        try {
            News news = newsMapper.toEntity(newsDTO);
            news = newsRepository.save(news);
            logger.info("News created successfully with ID: {}", news.getId());
            return newsMapper.toDto(news);
        } catch (Exception e) {
            logger.error("Error creating news: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to create news");
        }
    }

    // Получение всех новостей
    public List<NewsDTO> getAllNews() {
        try {
            List<NewsDTO> newsList = newsRepository.findAll().stream()
                    .map(newsMapper::toDto)
                    .toList();
            logger.info("Fetched {} news items", newsList.size());
            return newsList;
        } catch (Exception e) {
            logger.error("Error fetching all news: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to fetch news");
        }
    }

    // Получение новости по ID
    public NewsDTO getNewsById(Long id) {
        try {
            News news = newsRepository.findById(id)
                    .orElseThrow(() -> new NewsNotFoundException("News not found with ID: " + id));
            logger.info("Fetched news with ID: {}", id);
            return newsMapper.toDto(news);
        } catch (NewsNotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching news with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to fetch news");
        }
    }

    // Обновление новости
    public NewsDTO updateNews(Long id, NewsDTO newsDTO) {
        try {
            News existingNews = newsRepository.findById(id)
                    .orElseThrow(() -> new NewsNotFoundException("News not found with ID: " + id));

            if (!existingNews.getAuthor().getId().equals(newsDTO.getAuthorId())) {
                throw new IllegalArgumentException("Only the author of the news can update it");
            }

            // Обновляем данные новости
            existingNews.setTitle(newsDTO.getTitle());
            existingNews.setContent(newsDTO.getContent());
            // Любые другие поля, которые нужно обновить

            News updatedNews = newsRepository.save(existingNews);
            logger.info("News updated successfully with ID: {}", updatedNews.getId());
            return newsMapper.toDto(updatedNews);
        } catch (NewsNotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error updating news with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to update news");
        }
    }

    // Удаление новости
    public void deleteNews(Long id) {
        try {
            if (!newsRepository.existsById(id)) {
                throw new NewsNotFoundException("News not found with ID: " + id);
            }
            newsRepository.deleteById(id);
            logger.info("Deleted news with ID: {}", id);
        } catch (NewsNotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting news with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to delete news");
        }
    }
}