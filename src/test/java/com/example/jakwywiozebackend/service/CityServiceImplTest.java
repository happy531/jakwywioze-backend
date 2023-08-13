package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.entity.City;
import com.example.jakwywiozebackend.mapper.CityMapper;
import com.example.jakwywiozebackend.repository.CityRepository;
import com.example.jakwywiozebackend.service.impl.CityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class CityServiceImplTest {
    @Mock
    private CityMapper cityMapper;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCityById() {
        Long id = 1L;
        City city = new City();
        city.setId(id);
        when(cityRepository.findById(id)).thenReturn(java.util.Optional.of(city));
        CityDto cityDto = new CityDto();
        cityDto.setId(id);
        when(cityMapper.toCityDto(city)).thenReturn(cityDto);

        CityDto result = cityService.getCityById(id);
        assertEquals(id, result.getId());
    }

    @Test
    public void testGetAllCities() {
        List<City> cities = List.of(new City(), new City());
        when(cityRepository.findAll()).thenReturn(cities);
        List<CityDto> cityDtos = List.of(new CityDto(), new CityDto());
        when(cityMapper.toCityDtoList(cities)).thenReturn(cityDtos);

        List<CityDto> result = cityService.getAllCities();
        assertEquals(cityDtos.size(), result.size());
    }

    @Test
    public void testGetCitiesByNameSubstring() {
        String name = "test";
        List<City> cities = List.of(new City(), new City());
        when(cityRepository.findByNameContaining(name)).thenReturn(cities);
        List<CityDto> cityDtos = List.of(new CityDto(), new CityDto());
        when(cityMapper.toCityDtoList(cities)).thenReturn(cityDtos);

        List<CityDto> result = cityService.getCitiesByNameSubstring(name);
        assertEquals(cityDtos.size(), result.size());
    }
}
