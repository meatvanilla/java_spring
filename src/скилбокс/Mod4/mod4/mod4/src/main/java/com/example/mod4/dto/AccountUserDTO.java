package com.example.mod4.dto;


import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountUserDTO {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private List<NewsDTO> news;
    private List<CommentDTO> comments;
    @Override
    public String toString() {
        return "AccountUserDTO [id=" + id + ", username=" + username + ", password=" + password + "]";
    }
}
