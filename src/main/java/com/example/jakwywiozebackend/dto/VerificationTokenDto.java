package com.example.jakwywiozebackend.dto;

import com.example.jakwywiozebackend.entity.Comment;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class VerificationTokenDto {

    private Long id;
    private String token;
    private UserDto user;
}
