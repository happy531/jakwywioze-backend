package com.example.jakwywiozebackend.dto;

import com.example.jakwywiozebackend.entity.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Getter
@Setter
public class DynamicPointInfoDto {

    private Long id;
    User user;
    Point point;
    String city;
    String street;
    LocalDateTime startingDateTime;
    LocalDateTime endingDateTime;
    List<String> additionalWasteTypes;
}
