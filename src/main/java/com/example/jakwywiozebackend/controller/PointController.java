package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.*;
import com.example.jakwywiozebackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping
    public ResponseEntity<PointDto> createPoint(@RequestBody DynamicPointCreateDto pointDto) throws IOException, InterruptedException {
        return new ResponseEntity<>(pointService.createDynamicPoint(pointDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointDto> getPoint(@RequestBody @PathVariable Long id){
        return new ResponseEntity<>(pointService.getPoint(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PointDto>> getAllPoints(){
        return new ResponseEntity<>(pointService.getPoints(), HttpStatus.OK);
    }

//    @PutMapping("/{id}")
    public ResponseEntity<PointDto> addWasteType(@RequestBody @PathVariable Long id, @RequestBody WasteTypeDto wasteTypeDto){
        return new ResponseEntity<>(pointService.addWasteType(id, wasteTypeDto), HttpStatus.OK);
    }

    @PostMapping("/filtered")
    public ResponseEntity<FilterResponse> getFilteredPoints(@RequestBody FilterInfoDto filterInfoDto){
        return new ResponseEntity<>(pointService.getFilteredPoints(filterInfoDto), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PointDto>> getPointsAssignedToUser(@PathVariable Long id){
        return new ResponseEntity<>(pointService.getPointsAssignedToUser(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PointDto> updatePoint(@RequestBody PointDto pointDto){
        return new ResponseEntity<>(pointService.updatePoint(pointDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePoint(@PathVariable Long id){
        return new ResponseEntity<>(pointService.deletePoint(id), HttpStatus.OK);
    }
}
