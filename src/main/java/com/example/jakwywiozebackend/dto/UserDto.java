package com.example.jakwywiozebackend.dto;

import com.example.jakwywiozebackend.entity.Comment;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;
@Data
@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;
    private String username;
    @Length(min = 8, message = "Password should be at least 8 characters long")
    private String password;
    private List<DynamicPointInfo> dynamicPointInfoList;
    private List<Comment> comments;
}
