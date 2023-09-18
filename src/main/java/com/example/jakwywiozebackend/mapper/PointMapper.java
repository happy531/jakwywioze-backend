package com.example.jakwywiozebackend.mapper;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {
//    @Mapping(target = "cityId", ignore = true)
    Point toPoint(PointDto pointDto);
    @Mapping(target = "cityId", ignore = true)
    PointDto toPointDto(Point point);
    List<Point> toPointList(List<PointDto> pointDtos);
    List<PointDto> toPointDtoList(List<Point> points);
    City map(String cityName);
    String map(City value);
}
