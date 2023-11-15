package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);

    List<CommentDto> getCommentsForPoint(Long id);
}
