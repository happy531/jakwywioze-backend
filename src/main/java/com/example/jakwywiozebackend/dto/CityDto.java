package com.example.jakwywiozebackend.dto;

import lombok.Data;


@Data
public class CityDto {
    private Long id;
    private String name;
    private String voivodeship;
    private String county;
    private float longitude;
    private float latitude;
}
