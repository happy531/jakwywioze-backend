package com.example.jakwywiozebackend.dto;

import com.example.jakwywiozebackend.entity.Point;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class WasteTypeDto {
    private Long id;
    private String name;
    private List<Point> points;
}
