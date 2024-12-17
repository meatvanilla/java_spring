package com.example.mod4.dto;

import com.example.mod4.model.AccountUser;
import com.example.mod4.model.News;
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
