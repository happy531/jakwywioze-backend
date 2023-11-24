package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.entity.Comment;
import org.springframework.data.jpa.domain.Specification;


public class CommentSpecification {
    public static Specification<Comment> getCommentsByPoint(Long id) {
        if(id == null){
            return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("point").get("id"), id);
    }

    public static Specification<Comment> getCommentsByUser(Long id) {
        if(id == null){
            return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), id);
    }
}
