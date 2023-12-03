package com.example.jakwywiozebackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Data
@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;
    private String username;
    private String role;
    @Length(min = 8, message = "Password should be at least 8 characters long")
    private String password;
}
