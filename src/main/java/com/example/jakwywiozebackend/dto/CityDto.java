package com.example.jakwywiozebackend.dto;

import com.example.jakwywiozebackend.entity.Point;
import lombok.Data;

import java.util.List;

@Data
public class CityDto {
    private Long id;
    private String name;
    private String province;
    private float longitude;
    private float latitude;
    private List<Point> points;
}
