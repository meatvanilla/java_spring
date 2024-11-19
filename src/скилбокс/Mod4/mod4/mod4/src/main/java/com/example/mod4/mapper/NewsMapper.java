package com.example.mod4.mapper;

import com.example.mod4.dto.NewsDTO;
import com.example.mod4.entity.AccountUser;
import com.example.mod4.entity.Category;
import com.example.mod4.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface NewsMapper {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "comments", target = "comments") // Маппинг комментариев
    NewsDTO toDto(News news);

    @Mapping(source = "authorId", target = "author", qualifiedByName = "mapAuthorIdToAccountUser")
    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategoryIdToCategory")
    @Mapping(source = "comments", target = "comments") // Маппинг комментариев
    News toEntity(NewsDTO newsDTO);

    @Named("mapAuthorIdToAccountUser")
    default AccountUser mapAuthorIdToAccountUser(Long authorId) {
        if (authorId == null) {
            return null;
        }
        AccountUser author = new AccountUser();
        author.setId(authorId);  // Устанавливаем только ID
        return author;
    }

    @Named("mapCategoryIdToCategory")
    default Category mapCategoryIdToCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryId);  // Устанавливаем только ID
        return category;
    }
}