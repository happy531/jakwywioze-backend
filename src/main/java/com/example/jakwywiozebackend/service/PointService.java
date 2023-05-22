package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.PointDto;

import java.util.List;

public interface PointService {
    List<PointDto> getPoints();
    PointDto getPoint(Long id);
    PointDto createPoint(PointDto pointDto);
}
