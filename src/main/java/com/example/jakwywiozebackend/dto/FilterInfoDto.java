package com.example.jakwywiozebackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class FilterInfoDto {
    private List<String> wasteTypesNames;
    private String city;
    private int range;
}
