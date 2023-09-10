package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.*;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.WasteType;
import com.example.jakwywiozebackend.mapper.PointMapper;
import com.example.jakwywiozebackend.mapper.WasteTypeMapper;
import com.example.jakwywiozebackend.repository.PointRepository;
import com.example.jakwywiozebackend.repository.WasteTypeRepository;
import com.example.jakwywiozebackend.service.PointService;
import com.example.jakwywiozebackend.service.PointSpecification;
import com.example.jakwywiozebackend.utils.Utils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;
    private final WasteTypeMapper wasteTypeMapper;
    private final WasteTypeRepository wasteTypeRepository;
    private final CityServiceImpl cityService;

    @Override
    public List<PointDto> getPoints() {
        return addIdToPointDtos(pointMapper.toPointDtoList(pointRepository.findAll()));
    }

    @Override
    public PointDto getPoint(Long id) {
        return addIdToPointDto(pointMapper.toPointDto(pointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point not found"))));
    }

    @Override
    public PointDto createPoint(PointDto pointDto) {
        List<PointDto> points = getPoints();
        for (PointDto p : points) {
            if (p.getLon() == pointDto.getLon()
                    && p.getLat() == pointDto.getLat()) {
                throw new EntityExistsException("Point already exists");
            }
        }
        return addIdToPointDto(pointMapper.toPointDto(pointRepository.save(pointMapper.toPoint(pointDto))));
    }

    @Override
    public PointDto addWasteType(Long id, WasteTypeDto wasteTypeDto) {
        Point point = pointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point not found"));
        List<WasteType> wasteTypes = point.getWasteTypes();
        // only god can judge me
        int i = 0;
        while (i < wasteTypes.size()) {
            if (wasteTypes.get(i).getName().equals(wasteTypeDto.getName())) {
                return pointMapper.toPointDto(pointRepository.save(point));
            }
            i++;
        }
        if (wasteTypeRepository.findByName(wasteTypeDto.getName()).isPresent()) {
            wasteTypes.add(wasteTypeRepository.findByName(wasteTypeDto.getName()).get());
            return pointMapper.toPointDto(pointRepository.save(point));
        }
        wasteTypes.add(wasteTypeMapper.toWasteType(wasteTypeDto));
        point.setWasteTypes(wasteTypes);
        return addIdToPointDto(pointMapper.toPointDto(pointRepository.save(point)));
    }

    @Override
    public List<PointDto> getFilteredPoints(FilterInfoDto filterInfoDto) {
        Specification<Point> spec = Specification
                .where(PointSpecification.getPointByWasteTypes(filterInfoDto.getWasteTypesNames()));
        List<Point> pointsWithoutRange = pointRepository.findAll(spec);
        CityDto city = cityService.getCityByName(filterInfoDto.getCity());
        List<Point> pointsInRange = Utils.filterPointsByRange(pointsWithoutRange, city, filterInfoDto.getRange());
        Pageable pageable = PageRequest.of(filterInfoDto.getPage(), filterInfoDto.getItemsPerPage());
        PageDTO<Point> pointsPage = getPaginatedList(pointsInRange, pageable.getPageNumber(), pageable.getPageSize());
        List<Point> points = pointsPage.getContent();
        return addIdToPointDtos(pointMapper.toPointDtoList(points));
    }

    private PageDTO<Point> getPaginatedList(List<Point> list, int page, int pageSize) {
        int totalElements = list.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        // Calculate the start and end indices for the current page
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalElements);

        // Create a sublist for the current page
        List<Point> pageContent = list.subList(startIndex, endIndex);

        PageDTO<Point> pageDTO = new PageDTO<>();
        pageDTO.setContent(pageContent);
        pageDTO.setTotalPages(totalPages);
        pageDTO.setTotalElements(totalElements);

        return pageDTO;
    }

    private List<PointDto> addIdToPointDtos(List<PointDto> points){
        for (PointDto point:points) {
            if(cityService.getCityByName(point.getCity()) != null){
                point.setCityId(cityService.getCityByName(point.getCity()).getId());
            }
        }
        return points;
    }

    private PointDto addIdToPointDto(PointDto point){
        if(cityService.getCityByName(point.getCity()) != null) {
            point.setCityId(cityService.getCityByName(point.getCity()).getId());
        }
        return point;
    }
}
