package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.Comment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAll(Specification<Comment> spec);

}
