package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
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
    public ResponseEntity<List<CityDto>> getFilteredPoints(@RequestParam(value = "name") String nameSubstring){
        return new ResponseEntity<>(cityService.getCitiesByNameSubstring(nameSubstring), HttpStatus.OK);
    }
}
