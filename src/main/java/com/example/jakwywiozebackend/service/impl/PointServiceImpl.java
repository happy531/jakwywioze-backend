package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.entity.Point;
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
    private final PointMapper pointMapper;
    @Override
    public List<PointDto> getPoints() {
        return pointMapper.toPointDtoList(pointRepository.findAll());
    }

    @Override
    public PointDto getPoint(Long id) {
        return null;
    }

    @Override
    public PointDto createPoint(PointDto pointDto) {
        Point point = pointMapper.toPoint(pointDto);
        System.out.println(pointDto.toString());
        return pointMapper.toPointDto(pointRepository.save(point));
    }
}
