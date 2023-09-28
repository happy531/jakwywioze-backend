package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.mapper.PointMapper;
import com.example.jakwywiozebackend.repository.PointRepository;
import com.example.jakwywiozebackend.repository.WasteTypeRepository;
import com.example.jakwywiozebackend.service.impl.PointServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PointServiceTest {

    @InjectMocks
    private PointServiceImpl pointService;

    @Mock
    private CityService cityService;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointMapper pointMapper;

    @Mock
    private WasteTypeRepository wasteTypeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPoints() {
        List<Point> points = new ArrayList<>();
        points.add(new Point());
        points.add(new Point());

        when(pointRepository.findAll()).thenReturn(points);

        List<PointDto> pointDtos = new ArrayList<>();
        pointDtos.add(new PointDto());
        pointDtos.add(new PointDto());

        when(pointMapper.toPointDtoList(points)).thenReturn(pointDtos);

        List<PointDto> result = pointService.getPoints();

        assertEquals(2, result.size());
        verify(pointRepository, times(1)).findAll();
        verify(pointMapper, times(1)).toPointDtoList(points);
    }

    @Test
    public void testGetPoint() {
        Long id = 1L;

        Point point = new Point();
        when(pointRepository.findById(id)).thenReturn(java.util.Optional.of(point));

        PointDto pointDto = new PointDto();
        when(pointMapper.toPointDto(point)).thenReturn(pointDto);

        PointDto result = pointService.getPoint(id);

        assertEquals(pointDto, result);
        verify(pointRepository, times(1)).findById(id);
        verify(pointMapper, times(1)).toPointDto(point);
    }

    @Test
    public void testCreatePoint() {
        PointDto pointDto = new PointDto();
        pointDto.setLon(1.0F);
        pointDto.setLat(2.0F);

        List<Point> points = new ArrayList<>();
        points.add(new Point());

        when(pointRepository.findAll()).thenReturn(points);

        List<PointDto> pointDtos = new ArrayList<>();
        pointDtos.add(new PointDto());

        when(pointMapper.toPointDtoList(points)).thenReturn(pointDtos);

        Point point = new Point();
        when(pointMapper.toPoint(pointDto)).thenReturn(point);

        when(pointRepository.save(point)).thenReturn(point);

        when(pointMapper.toPointDto(point)).thenReturn(pointDto);

        PointDto result = pointService.createPoint(pointDto);

        assertEquals(pointDto, result);
        verify(pointRepository, times(1)).findAll();
        verify(pointMapper, times(1)).toPointDtoList(points);
        verify(pointMapper, times(1)).toPoint(pointDto);
        verify(pointRepository, times(1)).save(point);
    }

    @Test
    public void testAddWasteType_shouldThrowExceptionIfPointNotFound() {
        Long id = 1L;
        WasteTypeDto wasteTypeDto = new WasteTypeDto();

        when(pointRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pointService.addWasteType(id, wasteTypeDto));
        verify(pointRepository, times(1)).findById(id);
        verify(wasteTypeRepository, never()).findByName(anyString());
    }
}

