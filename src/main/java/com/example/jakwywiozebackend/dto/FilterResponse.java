package com.example.jakwywiozebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class FilterResponse {
    private List<PointDto> points;
    private int totalPoints;
}
