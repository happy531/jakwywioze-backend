package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.CityDto;

import java.util.List;

public interface CityService {
    CityDto getCityById(Long id);
    List<CityDto> getAllCities();
    List<CityDto> getCitiesByNameSubstring(String name);
}
