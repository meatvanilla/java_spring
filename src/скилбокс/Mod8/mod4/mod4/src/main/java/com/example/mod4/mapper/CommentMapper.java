package com.example.mod4.mapper;

import com.example.mod4.dto.CommentDTO;
import com.example.mod4.model.Comment;
import com.example.mod4.model.News;
import com.example.mod4.model.AccountUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "news", source = "newsId")
    @Mapping(target = "author", source = "authorId")
    Comment toEntity(CommentDTO commentDTO);

    @Mapping(target = "newsId", source = "news.id")
    @Mapping(target = "authorId", source = "author.id")
    CommentDTO toDto(Comment comment);

    default News map(long newsId) {
        News news = new News();
        news.setId(newsId);
        return news;
    }

    default AccountUser mapToAuthor(long authorId) {
        AccountUser author = new AccountUser();
        author.setId(authorId);
        return author;
    }
}