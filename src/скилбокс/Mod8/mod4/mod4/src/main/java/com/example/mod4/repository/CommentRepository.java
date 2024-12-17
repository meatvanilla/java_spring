package com.example.mod4.repository;

import com.example.mod4.model.AccountUser;
import com.example.mod4.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
