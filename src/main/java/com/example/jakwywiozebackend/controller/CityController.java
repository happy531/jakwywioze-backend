package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.CityCoordsRequest;
import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.service.CityService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
@Validated
public class CityController {
    private final CityService cityService;

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCity(@RequestBody @PathVariable Long id){
        return new ResponseEntity<>(cityService.getCityById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> getCities(){
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<CityDto>> getFilteredCities(@RequestParam(value = "name") @Length(min = 3, message = "Name should be at least 3 characters") String nameSubstring){
        return new ResponseEntity<>(cityService.getCitiesByNameSubstring(nameSubstring), HttpStatus.OK);
    }

    @PostMapping("/closest")
    public ResponseEntity<CityDto> getClosestCity(@RequestBody CityCoordsRequest cityCoordsRequest){
        return new ResponseEntity<>(cityService.getClosestCity(cityCoordsRequest), HttpStatus.OK);
    }
}
