package com.example.jakwywiozebackend.mapper;

import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.entity.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City toCity(CityDto cityDto);
    CityDto toCityDto(City city);
    List<City> toCityList(List<CityDto> cityDtos);
    List<CityDto> toCityDtoList(List<City> citys);
}
