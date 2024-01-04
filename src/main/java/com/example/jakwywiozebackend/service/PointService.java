package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.*;

import java.io.IOException;
import java.util.List;

public interface PointService {
    List<PointDto> getPoints();

    PointDto getPoint(Long id);

    PointDto createDynamicPoint(DynamicPointCreateDto pointDto) throws IOException, InterruptedException;

    PointDto addWasteType(Long id, WasteTypeDto wasteTypeDto);

    FilterResponse getFilteredPoints(FilterInfoDto filterInfoDto);

    List<PointDto> getPointsAssignedToUser(Long userId);

    PointDto updatePoint(PointUpdateDto pointDto) throws IOException, InterruptedException;

    String deletePoint(Long id);
}