package com.example.jakwywiozebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

}
