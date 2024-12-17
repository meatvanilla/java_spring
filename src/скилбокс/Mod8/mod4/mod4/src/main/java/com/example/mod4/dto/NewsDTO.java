package com.example.mod4.dto;

import com.example.mod4.model.Comment;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NewsDTO {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Long authorId; // ID автора
    @NotNull
    private Long categoryId; // ID категории
    private List<CommentDTO> comments; // Поле для хранения комментариев
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Override
    public String toString() {
        return "NewsDTO [id=" + id + ", id=" + id + ", title=" + title + "]";
    }
}