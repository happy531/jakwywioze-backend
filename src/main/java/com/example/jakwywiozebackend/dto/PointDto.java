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
public class PointDto {

    private Long id;
    private String name;
    private String openingHours;
    private float lon;
    private float lat;
    private String city;
    private String street;
    private String zipcode;
    private Boolean type;
    private DynamicPointInfo dynamicPointInfo;
    private List<WasteType> wasteTypes;
    private List<Comment> comments;
}
