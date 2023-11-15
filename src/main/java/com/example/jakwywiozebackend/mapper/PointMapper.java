package com.example.jakwywiozebackend.mapper;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {
    @Mapping(source = "dynamicPointInfo.user", target = "dynamicPointInfo.user.id")
    Point toPoint(PointDto pointDto);
    @Mapping(source = "dynamicPointInfo.user.id", target = "dynamicPointInfo.user")
    PointDto toPointDto(Point point);
    List<Point> toPointList(List<PointDto> pointDtos);
    List<PointDto> toPointDtoList(List<Point> points);
    City map(String cityName);
    String map(City value);
}
