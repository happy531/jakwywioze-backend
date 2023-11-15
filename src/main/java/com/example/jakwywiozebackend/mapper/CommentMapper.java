package com.example.jakwywiozebackend.mapper;

import com.example.jakwywiozebackend.dto.CommentDto;
import com.example.jakwywiozebackend.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "user", target = "user.id")
    @Mapping(source = "point", target = "point.id")
    Comment toComment(CommentDto dynamicPointInfoDto);
    @Mapping(source = "user.id", target = "user")
    @Mapping(source = "point.id", target = "point")
    CommentDto toCommentDto(Comment dynamicPointInfo);
    List<Comment> toCommentList(List<CommentDto> commentDtos);
    List<CommentDto> toCommentDtoList(List<Comment> points);
}
