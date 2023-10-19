package com.example.jakwywiozebackend.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    @Size(min = 8, message = "Password has to be at least 8 characters long")
    private String password;
}
