package com.example.jakwywiozebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneratedTokenDto {
    private String email;
    private String token;
    private Long exp;
}
