package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.FilterInfoDto;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.WasteType;
import com.example.jakwywiozebackend.mapper.PointMapper;
import com.example.jakwywiozebackend.mapper.WasteTypeMapper;
import com.example.jakwywiozebackend.repository.PointRepository;
import com.example.jakwywiozebackend.repository.WasteTypeRepository;
import com.example.jakwywiozebackend.service.PointService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
    private static final double EARTH_RADIUS = 6371.0; // Earth's radius in kilometers

    @Override
    public List<PointDto> getPoints() {
        return pointMapper.toPointDtoList(pointRepository.findAll());
    }

    @Override
    public PointDto getPoint(Long id) {
        return pointMapper.toPointDto(pointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point not found")));
    }

    @Override
    public PointDto createPoint(PointDto pointDto) {
        List<PointDto> points = getPoints();
        for (PointDto p: points) {
            if(p.getLon() == pointDto.getLon()
                    && p.getLat() == pointDto.getLat()){
                throw new EntityExistsException("Point already exists");
            }
        }
        return pointMapper.toPointDto(pointRepository.save(pointMapper.toPoint(pointDto)));
    }

    @Override
    public PointDto addWasteType(Long id, WasteTypeDto wasteTypeDto) {
        Point point = pointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point not found"));
        List<WasteType> wasteTypes = point.getWasteTypes();
        // only god can judge me
        int i = 0;
        while (i < wasteTypes.size()){
            if(wasteTypes.get(i).getName().equals(wasteTypeDto.getName())) {
                return pointMapper.toPointDto(pointRepository.save(point));
            }
            i++;
        }
        if(wasteTypeRepository.findByName(wasteTypeDto.getName()).isPresent()){
            wasteTypes.add(wasteTypeRepository.findByName(wasteTypeDto.getName()).get());
            return pointMapper.toPointDto(pointRepository.save(point));
        }
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

    private static double calculateRange(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    @Override
    public List<PointDto> getFilteredPoints(FilterInfoDto filterInfoDto) {
        String city = filterInfoDto.getCity();
        List<String> wasteTypes = filterInfoDto.getWasteTypesNames();

        List<Point> points = pointRepository.findAllByCityAndWasteTypeIn(city, wasteTypes);

        // TODO fix
        points.forEach(point -> point.setDynamicPointInfo(null));

        // TODO get lat and lon from db or current current user location if he shares it
        double poznanLon = 52.4064;
        double poznanLat = 16.9252;

        // TODO postgis
        List<Point> pointsInRange = new ArrayList<>();
        points.forEach(point -> {
            if (calculateRange(poznanLat, poznanLon, point.getLat(), point.getLon()) <= filterInfoDto.getRange()) {
                pointsInRange.add(point);
            }
        });

        return pointMapper.toPointDtoList(pointsInRange);
    }
}
