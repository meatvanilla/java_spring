package com.example.mod4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private long newsId;
    private long authorId;
}
