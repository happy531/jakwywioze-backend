package com.example.jakwywiozebackend.dto;

import lombok.Data;

import java.util.List;
@Data
public class FilterInfoDto {
    private List<String> wasteTypesNames;
    private String city;
    private int range;
}
