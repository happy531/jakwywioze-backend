package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.mapper.PointMapper;
import com.example.jakwywiozebackend.repository.PointRepository;
import com.example.jakwywiozebackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    @Override
    public List<PointDto> getPoints() {
        return pointRepository.findAll();
    }

    @Override
    public PointDto getPoint(Long id) {
        return null;
    }
}
