package com.example.jakwywiozebackend.dto;

import com.example.jakwywiozebackend.entity.Comment;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import com.example.jakwywiozebackend.entity.WasteType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private List<DynamicPointInfo> dynamicPointInfoList;
    private List<Comment> comments;
}
