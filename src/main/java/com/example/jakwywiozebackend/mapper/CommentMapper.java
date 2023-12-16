package com.example.jakwywiozebackend.mapper;

import com.example.jakwywiozebackend.dto.comment.CommentBasic;
import com.example.jakwywiozebackend.dto.comment.CommentDto;
import com.example.jakwywiozebackend.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "user", target = "user.id")
    @Mapping(source = "point", target = "point.id")
    @Mapping(source = "createdAt", target = "createdAt")
    Comment toComment(CommentDto commentDto);
    @Mapping(source = "user.id", target = "user")
    @Mapping(source = "point.id", target = "point")
    @Mapping(source = "createdAt", target = "createdAt")
    CommentDto toCommentDto(Comment comment);
    @Mapping(source = "user.username", target = "user")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "user.id", target = "userId")
    CommentBasic toCommentBasic(Comment comment);
    List<Comment> toCommentList(List<CommentDto> commentDtos);
    List<CommentDto> toCommentDtoList(List<Comment> comments);
    List<CommentBasic> toCommentBasicList(List<Comment> comments);
}
