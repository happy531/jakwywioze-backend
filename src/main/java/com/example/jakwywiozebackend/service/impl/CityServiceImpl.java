package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.CityCoordsRequest;
import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.entity.City;
import com.example.jakwywiozebackend.mapper.CityMapper;
import com.example.jakwywiozebackend.repository.CityRepository;
import com.example.jakwywiozebackend.service.CityService;
import com.example.jakwywiozebackend.service.CitySpecification;
import com.example.jakwywiozebackend.utils.Utils;
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
        return cityRepository.findById(id).map(cityMapper::toCityDto).orElse(null);
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityMapper.toCityDtoList(cityRepository.findAll());
    }

    @Override
    public List<CityDto> getCitiesByNameSubstring(String name) {
        Specification<City> spec = CitySpecification.getCityBySubstring(name);
        List<City> cities = cityRepository.findAll(spec);
        return cityMapper.toCityDtoList(cities);
    }

    @Override
    public CityDto getCityByName(String name) {
        City city = cityRepository.findByName(name);
        return cityMapper.toCityDto(city);
    }

    @Override
    public CityDto getClosestCity(CityCoordsRequest coordsRequest) {
        List<City> cities = cityRepository.findAll();
        float min = Float.MAX_VALUE;
        float distance;
        City closestCity = null;
        for (City city: cities) {
            distance = Utils.distanceFrom(coordsRequest.getLatitude(), coordsRequest.getLongitude(), city.getLatitude(), city.getLongitude());
            if(distance<min){
                min = distance;
                closestCity = city;
            }
        }
        return cityMapper.toCityDto(closestCity);
    }
}
