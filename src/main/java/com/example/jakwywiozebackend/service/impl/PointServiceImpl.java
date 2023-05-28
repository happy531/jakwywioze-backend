package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.WasteType;
import com.example.jakwywiozebackend.mapper.PointMapper;
import com.example.jakwywiozebackend.mapper.WasteTypeMapper;
import com.example.jakwywiozebackend.repository.PointRepository;
import com.example.jakwywiozebackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;
    private final WasteTypeMapper wasteTypeMapper;

    @Override
    public List<PointDto> getPoints() {
        return pointMapper.toPointDtoList(pointRepository.findAll());
    }

    @Override
    public PointDto getPoint(Long id) {
        return pointMapper.toPointDto(pointRepository.findById(id).orElseThrow(() -> new RuntimeException("null point")));
    }

    @Override
    public PointDto createPoint(PointDto pointDto) {
        Point point = pointMapper.toPoint(pointDto);
        return pointMapper.toPointDto(pointRepository.save(point));
    }

    @Override
    public PointDto addWasteType(Long id, WasteTypeDto wasteTypeDto) {
        Point point = new Point();
        if(pointRepository.findById(id).isPresent()){
            point = pointRepository.findById(id).get();
        }
        List<WasteType> wasteTypes = point.getWasteTypes();
        wasteTypes.add(wasteTypeMapper.toWasteType(wasteTypeDto));
        point.setWasteTypes(wasteTypes);
        return pointMapper.toPointDto(pointRepository.save(point));
    }

    @Override
    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        List<Point> points = pointRepository.findAll();
        for (Point point : points) {
            cities.add(point.getCity());
        }
        return cities;
    }
}
