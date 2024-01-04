package com.example.jakwywiozebackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class PointUpdateDto {

    private Long id;
    private String name;
    private String openingHours;
    private float lon;
    private float lat;
    private String city;
    private Long cityId;
    private String street;
    private String zipcode;
    private Boolean isDynamic;
    private String phoneNumber;
    private String website;
    private String imageLink;
    private DynamicPointInfoDto dynamicPointInfo;
    private List<String> wasteTypes;
}
