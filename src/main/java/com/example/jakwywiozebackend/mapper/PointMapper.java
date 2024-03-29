package com.example.jakwywiozebackend.mapper;
import com.example.jakwywiozebackend.dto.DynamicPointCreateDto;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.dto.PointUpdateDto;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.City;
import org.mapstruct.*;


import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {
    @Mapping(source = "dynamicPointInfo.user", target = "dynamicPointInfo.user.id")
    @Mapping(source = "dynamicPointInfo.description", target = "dynamicPointInfo.description")
    Point toPoint(PointDto pointDto);
    @Mapping(source = "dynamicPointInfo.user", target = "dynamicPointInfo.user.id")
    @Mapping(source = "dynamicPointInfo.description", target = "dynamicPointInfo.description")
    @Mapping(target = "wasteTypes", ignore = true)
    Point toPoint(DynamicPointCreateDto pointDto);
    @Mapping(source = "dynamicPointInfo.user.id", target = "dynamicPointInfo.user")
    @Mapping(source = "dynamicPointInfo.description", target = "dynamicPointInfo.description")
    PointDto toPointDto(Point point);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dynamicPointInfo.user", ignore = true)
    @Mapping(target = "lat", ignore = true)
    @Mapping(target = "wasteTypes", ignore = true)
    @Mapping(target = "lon", ignore = true)
    void updatePointFromDto(PointUpdateDto dto, @MappingTarget Point point);
    List<Point> toPointList(List<PointDto> pointDtos);
    List<PointDto> toPointDtoList(List<Point> points);
    City map(String cityName);
    String map(City value);
}
