package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.FilterInfoDto;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.dto.WasteTypeDto;

import java.util.List;

public interface PointService {
    List<PointDto> getPoints();

    PointDto getPoint(Long id);

    PointDto createPoint(PointDto pointDto);

    PointDto addWasteType(Long id, WasteTypeDto wasteTypeDto);

    List<String> getCities();

    List<PointDto> getFilteredPoints(FilterInfoDto filterInfoDto);
}