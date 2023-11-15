package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.comment.CommentBasic;
import com.example.jakwywiozebackend.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);

    List<CommentBasic> getCommentsForPoint(Long id);
}
