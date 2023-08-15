package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.entity.City;
import com.example.jakwywiozebackend.mapper.CityMapper;
import com.example.jakwywiozebackend.repository.CityRepository;
import com.example.jakwywiozebackend.service.CityService;
import com.example.jakwywiozebackend.service.CitySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    @Override
    public CityDto getCityById(Long id) {
        return cityMapper.toCityDto(cityRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityMapper.toCityDtoList(cityRepository.findAll());
    }

    @Override
    public List<CityDto> getCitiesByNameSubstring(String substring) {
        Specification<City> citySpecification = Specification
                .where(CitySpecification.getCityBySubstring(substring));
        List<City> cities = cityRepository.findAll(citySpecification);
        return cityMapper.toCityDtoList(cities);
    }}
