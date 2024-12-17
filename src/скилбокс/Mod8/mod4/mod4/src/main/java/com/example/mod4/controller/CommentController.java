package com.example.mod4.controller;

import com.example.mod4.dto.AccountUserDTO;
import com.example.mod4.dto.CommentDTO;
import com.example.mod4.dto.NewsDTO;
import com.example.mod4.model.AccountUser;
import com.example.mod4.repository.AccountUserRepository;
import com.example.mod4.service.AccountUserService;
import com.example.mod4.service.CommentService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final AccountUserService accountUserService;
    private final CommentService commentService;
    private final AccountUserRepository accountUserRepository;

    @Autowired
    public CommentController(CommentService commentService ,
                             AccountUserRepository accountUserRepository,
                             AccountUserService accountUserService) {

        this.accountUserService = accountUserService;
        this.commentService = commentService;
        this.accountUserRepository = accountUserRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        AccountUser accountUser = accountUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        commentDTO.setAuthorId(accountUser.getId());
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateNews(@PathVariable Long id,
                                                 @RequestBody @Valid CommentDTO commentDTO,
                                                 Authentication authentication) {
        Optional<CommentDTO> comment = commentService.getCommentById(id);
        AccountUserDTO user = accountUserService.getUserById(comment.get().getAuthorId());
        if (!user.getUsername().equals(authentication.getName())) {
            throw new AccessDeniedException("Нет доступа");
        }
        CommentDTO updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, Authentication authentication) {
        Optional<CommentDTO> comment = commentService.getCommentById(id);
        AccountUserDTO user = accountUserService.getUserById(comment.get().getAuthorId());
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")) &&
                !user.getUsername().equals(authentication.getName())) {
            throw new AccessDeniedException("Нет доступа");
        }
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}