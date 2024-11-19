package com.example.mod4.mapper;


import com.example.mod4.dto.CategoryDTO;
import com.example.mod4.dto.NewsDTO;
import com.example.mod4.entity.Category;
import com.example.mod4.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public abstract class CategoryMapper {

    @Autowired
    protected NewsMapper newsMapper; // Внедряем NewsMapper

    @Mapping(target = "news", source = "news")
    public abstract CategoryDTO toDTO(Category category);

    @Mapping(target = "news", source = "news")
    public abstract Category toEntity(CategoryDTO categoryDTO);

    // Преобразования новостей с использованием внедрённого NewsMapper
    protected List<NewsDTO> toNewsDTOList(List<News> newsList) {
        return Optional.ofNullable(newsList) // Проверка на null
                .map(list -> list.stream()
                        .map(newsMapper::toDto) // Используем внедрённый NewsMapper
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()); // Возвращаем пустой список, если null
    }

    protected List<News> toNewsList(List<NewsDTO> newsDTOList) {
        return Optional.ofNullable(newsDTOList) // Проверка на null
                .map(list -> list.stream()
                        .map(newsMapper::toEntity) // Используем внедрённый NewsMapper
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()); // Возвращаем пустой список, если null
    }
}