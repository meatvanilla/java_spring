package com.example.mod4.controller;

import com.example.mod4.dto.AccountUserDTO;
import com.example.mod4.dto.NewsDTO;
import com.example.mod4.model.AccountUser;
import com.example.mod4.repository.AccountUserRepository;
import com.example.mod4.service.AccountUserService;
import com.example.mod4.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final AccountUserService accountUserService;
    private final NewsService newsService;
    private final AccountUserRepository accountUserRepository;

    @Autowired
    public NewsController(NewsService newsService, AccountUserService accountUserService, AccountUserRepository accountUserRepository) {
        this.newsService = newsService;
        this.accountUserService = accountUserService;
        this.accountUserRepository = accountUserRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody @Valid NewsDTO newsDTO, Authentication authentication) {
        AccountUser accountUser = accountUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        newsDTO.setAuthorId(accountUser.getId());
        NewsDTO createdNews = newsService.createNews(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNews);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<NewsDTO> news = newsService.getAllNews();
        return ResponseEntity.ok(news);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        NewsDTO news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id, @RequestBody @Valid NewsDTO newsDTO, Authentication authentication) {
        AccountUserDTO user = accountUserService.getUserById(newsService.getNewsById(id).getAuthorId());
        if (!user.getUsername().equals(authentication.getName())) {
            throw new AccessDeniedException("Нет доступа");
        }
        NewsDTO updatedNews = newsService.updateNews(id, newsDTO);
        return ResponseEntity.ok(updatedNews);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id, Authentication authentication) {
        AccountUserDTO user = accountUserService.getUserById(newsService.getNewsById(id).getAuthorId());
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")) &&
                !user.getUsername().equals(authentication.getName())) {
            throw new AccessDeniedException("Нет доступа");
        }
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

}