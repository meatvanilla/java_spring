package com.example.mod4.dto;

import com.example.mod4.model.News;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "category")
    private List<NewsDTO> news;
}
