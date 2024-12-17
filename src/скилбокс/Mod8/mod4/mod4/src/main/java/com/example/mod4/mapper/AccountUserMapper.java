package com.example.mod4.mapper;

import com.example.mod4.dto.AccountUserDTO;
import com.example.mod4.dto.CommentDTO;
import com.example.mod4.dto.NewsDTO;
import com.example.mod4.model.AccountUser;
import com.example.mod4.model.Comment;
import com.example.mod4.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NewsMapper.class, CommentMapper.class})
public interface AccountUserMapper {
    @Mapping(source = "news", target = "news")
    @Mapping(source = "comments", target = "comments") // Добавьте маппинг для комментариев
    AccountUserDTO toDTO(AccountUser accountUser);

    @Mapping(source = "news", target = "news")
    @Mapping(source = "comments", target = "comments") // Добавьте маппинг для комментариев
    AccountUser toEntity(AccountUserDTO accountUserDTO);

    List<NewsDTO> newsToNewsDTOs(List<News> news);
    List<News> newsDTOsToNews(List<NewsDTO> newsDTOs);

    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments); // Маппинг комментариев
    List<Comment> commentDTOsToComments(List<CommentDTO> commentDTOs); // Обратный маппинг комментариев
}