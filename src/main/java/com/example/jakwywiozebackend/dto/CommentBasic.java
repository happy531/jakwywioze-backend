package com.example.jakwywiozebackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentBasic {
    private Long id;
    @NotBlank
    private String text;
    private UserDto user;
}
