package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.comment.CommentBasic;
import com.example.jakwywiozebackend.dto.comment.CommentDto;
import com.example.jakwywiozebackend.entity.Comment;
import com.example.jakwywiozebackend.mapper.CommentMapper;
import com.example.jakwywiozebackend.repository.CommentRepository;
import com.example.jakwywiozebackend.service.CommentService;
import com.example.jakwywiozebackend.service.CommentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentMapper.toComment(commentDto);
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentBasic> getCommentsForPoint(Long id) {
        Specification<Comment> spec = Specification
                .where(CommentSpecification.getCommentsByPoint(id));
        return commentMapper.toCommentBasicList(commentRepository.findAll(spec));
    }
}
