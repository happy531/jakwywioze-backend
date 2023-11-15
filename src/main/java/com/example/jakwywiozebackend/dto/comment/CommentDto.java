package com.example.jakwywiozebackend.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    @NotBlank
    private String text;
    @NotNull
    private Long point;
    @NotNull
    private Long user;
    private LocalDateTime createdAt;
}
