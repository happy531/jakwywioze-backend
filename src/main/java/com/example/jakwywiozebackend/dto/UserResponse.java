package com.example.jakwywiozebackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String username;
}
