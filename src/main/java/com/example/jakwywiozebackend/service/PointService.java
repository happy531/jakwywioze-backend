package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.*;

import java.util.List;

public interface PointService {
    List<PointDto> getPoints();

    PointDto getPoint(Long id);

    PointDto createPoint(PointDto pointDto);

    PointDto addWasteType(Long id, WasteTypeDto wasteTypeDto);

    FilterResponse getFilteredPoints(FilterInfoDto filterInfoDto);
}