package com.example.jakwywiozebackend.dto.comment;

import com.example.jakwywiozebackend.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {
    private Long id;
    @NotBlank
    private String text;
    private UserDto user;
}
