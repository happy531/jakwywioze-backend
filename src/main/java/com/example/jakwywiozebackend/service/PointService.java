package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.*;

import java.io.IOException;
import java.util.List;

public interface PointService {
    List<PointDto> getPoints();

    PointDto getPoint(Long id);

    PointDto createPoint(PointDto pointDto) throws IOException, InterruptedException;

    PointDto addWasteType(Long id, WasteTypeDto wasteTypeDto);

    FilterResponse getFilteredPoints(FilterInfoDto filterInfoDto);

    List<PointDto> getPointsAssignedToUser(Long userId);
}