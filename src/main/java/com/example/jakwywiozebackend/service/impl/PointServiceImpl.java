package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.*;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.WasteType;
import com.example.jakwywiozebackend.mapper.DynamicPointInfoMapper;
import com.example.jakwywiozebackend.mapper.PointMapper;
import com.example.jakwywiozebackend.mapper.WasteTypeMapper;
import com.example.jakwywiozebackend.repository.PointRepository;
import com.example.jakwywiozebackend.repository.WasteTypeRepository;
import com.example.jakwywiozebackend.service.CityService;
import com.example.jakwywiozebackend.service.DynamicPointInfoService;
import com.example.jakwywiozebackend.service.PointService;
import com.example.jakwywiozebackend.service.PointSpecification;
import com.example.jakwywiozebackend.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;
    private final WasteTypeMapper wasteTypeMapper;
    private final WasteTypeRepository wasteTypeRepository;
    private final CityService cityService;
    private final DynamicPointInfoService dynamicPointService;
    private final DynamicPointInfoMapper dynamicPointMapper;

    @Override
    public List<PointDto> getPoints() {
        return addIdToPointDtos(pointMapper.toPointDtoList(pointRepository.findAll()));
    }

    @Override
    public PointDto getPoint(Long id) {
        return addIdToPointDto(pointMapper.toPointDto(pointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point not found"))));
    }

    @Override
    public PointDto createPoint(PointDto pointDto) throws IOException, InterruptedException {
//        List<PointDto> points = getPoints();
//        for (PointDto p : points) {
//            if (p.getLon() == pointDto.getLon()
//                    && p.getLat() == pointDto.getLat()) {
//                throw new EntityExistsException("Point already exists");
//            }
//        }

        Point point = pointMapper.toPoint(pointDto);
        point.setIsDynamic(false);

        if (pointDto.getDynamicPointInfo() != null) {
            DynamicPointInfo dynamicPointInfo = dynamicPointMapper.toDynamicPointInfo(dynamicPointService.createDynamicPointInfo(pointDto.getDynamicPointInfo()));
            point.setDynamicPointInfo(dynamicPointInfo);
            point.setIsDynamic(true);

            Utils.setLatAndLonForDynamicPointByAddress(point);
        }
        return addIdToPointDto(pointMapper.toPointDto(pointRepository.save(point)));
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
    public FilterResponse getFilteredPoints(FilterInfoDto filterInfoDto) {
        List<String> filterWasteTypes = filterInfoDto.getWasteTypesNames();
        Specification<Point> spec = Specification
                .where(PointSpecification.getPointByWasteTypes(filterWasteTypes));
        List<Point> points = pointRepository.findAll(spec);
        Optional<CityDto> cityOptional = Optional.ofNullable(cityService.getCityById(filterInfoDto.getCityId()));
        if (cityOptional.isPresent()) {
            CityDto city = cityOptional.get();
            points = (filterInfoDto.getRange() != 0)
                    ? Utils.filterPointsByRange(points, city, filterInfoDto.getRange())
                    : points.stream().filter(point -> point.getCity().equals(city.getName())).collect(Collectors.toList());

            points.sort((p1, p2) -> {
                int matches1 = (int) p1.getWasteTypes().stream().map(WasteType::getName).filter(filterWasteTypes::contains).count();
                int matches2 = (int) p2.getWasteTypes().stream().map(WasteType::getName).filter(filterWasteTypes::contains).count();
                if (matches1 != matches2) {
                    return matches2 - matches1;
                } else {
                    double range1 = Utils.calculateRange(city.getLatitude(), city.getLongitude(), p1.getLat(), p1.getLon());
                    double range2 = Utils.calculateRange(city.getLatitude(), city.getLongitude(), p2.getLat(), p2.getLon());
                    return Double.compare(range1, range2);
                }
            });
        }

        Pageable pageable = PageRequest.of(filterInfoDto.getPage(), filterInfoDto.getItemsPerPage());
        PageDTO<Point> pointsPage = getPaginatedList(points, pageable.getPageNumber(), pageable.getPageSize());
        List<Point> pointsPaged = pointsPage.getContent();
        return new FilterResponse(addIdToPointDtos(pointMapper.toPointDtoList(pointsPaged)), points.size());
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

    private List<PointDto> addIdToPointDtos(List<PointDto> points) {
        for (PointDto point : points) {
            if (cityService.getCityByName(point.getCity()) != null) {
                point.setCityId(cityService.getCityByName(point.getCity()).getId());
            }
        }
        return points;
    }

    private PointDto addIdToPointDto(PointDto point) {
        if (cityService.getCityByName(point.getCity()) != null) {
            point.setCityId(cityService.getCityByName(point.getCity()).getId());
        }
        return point;
    }
}
