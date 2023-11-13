package com.example.jakwywiozebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    @NotBlank
    private String text;
    @NotNull
    private Long point;
    @NotNull
    private Long user;
}
