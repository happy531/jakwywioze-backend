package com.example.jakwywiozebackend.dto;

import lombok.Data;


@Data
public class CityDto {
    private Long id;
    private String name;
    private String province;
    private float longitude;
    private float latitude;
}
