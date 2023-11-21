package com.example.jakwywiozebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 1, message = "Password should be at least 8 characters")
    private String password;

}
