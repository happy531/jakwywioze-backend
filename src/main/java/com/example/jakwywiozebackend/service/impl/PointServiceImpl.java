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
import com.example.jakwywiozebackend.service.PointSpecification;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;
    private final WasteTypeMapper wasteTypeMapper;
    private final WasteTypeRepository wasteTypeRepository;

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
            cities.add(point.getCity().getName());
        }
        return cities;
    }

    private List<PointDto> getFilteredPointsWithAllInfo(FilterInfoDto filterInfoDto) {
        String city = filterInfoDto.getCity();
        List<String> wasteTypes = filterInfoDto.getWasteTypesNames();
        int range = filterInfoDto.getRange();

        List<Point> pointsWithAccurateWasteTypesAndCity = new ArrayList<>();
        List<Point> points = pointRepository.findAll();
        points.forEach(point -> {
            // names has at least 1 element from wasteTypes
            List<String> names = point.getWasteTypes().stream()
                    .map(WasteType::getName)
                    .collect(Collectors.toList());
            if(point.getCity().getName().equals(city) && !Collections.disjoint(names, wasteTypes)) {
                pointsWithAccurateWasteTypesAndCity.add(point);
            }

            // TODO fix
            point.setDynamicPointInfo(null);
        });

        if(range > 0) {
            // TODO get lat and lon from db or current current user location if he shares it
            double poznanLon = 52.4064;
            double poznanLat = 16.9252;

            List<Point> pointsInRange = new ArrayList<>();
            pointsWithAccurateWasteTypesAndCity.forEach(point -> {
                if (UtilsServiceImpl.calculateRange(poznanLat, poznanLon, point.getLat(), point.getLon()) <= filterInfoDto.getRange()) {
                    pointsInRange.add(point);
                }
            });

            return pointMapper.toPointDtoList(pointsInRange);
        }
        return pointMapper.toPointDtoList(pointsWithAccurateWasteTypesAndCity);
    }

    private List<PointDto> getFilteredPointsOnlyCity(FilterInfoDto filterInfoDto) {
        String city = filterInfoDto.getCity();

        List<Point> points = pointRepository.findAll();

        // TODO fix
        points.forEach(point -> point.setDynamicPointInfo(null));

        // TODO get lat and lon from db or current current user location if he shares it
        double poznanLon = 52.4064;
        double poznanLat = 16.9252;
        List<Point> pointsInCity = new ArrayList<>();

        points.forEach(point -> {
            if (point.getCity().getName().equals(city)) {
                pointsInCity.add(point);
            }
        });

        return pointMapper.toPointDtoList(pointsInCity);
    }

    private List<PointDto> getFilteredPointsOnlyWasteType(FilterInfoDto filterInfoDto) {

        List<Point> points = pointRepository.findAll();

        // TODO fix
        points.forEach(point -> point.setDynamicPointInfo(null));
        List<Point> filteredPoints = new ArrayList<>();

        points.forEach(point -> {
            if(point.getWasteTypes().stream().anyMatch(wasteType -> filterInfoDto.getWasteTypesNames().contains(wasteType.getName()))){
                filteredPoints.add(point);
            }
        });


        return pointMapper.toPointDtoList(filteredPoints);
    }

    @Override
    public List<PointDto> getFilteredPoints(FilterInfoDto filterInfoDto) {
        Specification<Point> spec = Specification.where(PointSpecification.getPointByCity(filterInfoDto.getCity()))
                .and(PointSpecification.getPointByWasteTypes(filterInfoDto.getWasteTypesNames()));
        List<Point> points = pointRepository.findAll(spec);
        return pointMapper.toPointDtoList(points);
    }

    @Override
    public List<PointDto> findPoints(String city, List<String> wasteTypes) {
        Specification<Point> spec = Specification.where(PointSpecification.getPointByCity(city))
                .and(PointSpecification.getPointByWasteTypes(wasteTypes));
        List<Point> points = pointRepository.findAll(spec);
        return pointMapper.toPointDtoList(points);
    }
}
