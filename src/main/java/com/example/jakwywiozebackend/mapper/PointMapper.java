package com.example.jakwywiozebackend.mapper;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.entity.Point;
public interface PointMapper {
    Point toPoint(PointDto pointDto);
    PointDto toPointDto(Point point);
}
