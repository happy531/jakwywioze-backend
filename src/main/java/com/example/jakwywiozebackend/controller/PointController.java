package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping
    public ResponseEntity<PointDto> createPoint(@RequestBody PointDto pointDto){
        return new ResponseEntity<>(pointService.createPoint(pointDto), HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> apiTest(){
        return new ResponseEntity<>("Jest git", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PointDto>> getAllPoints(){
        return new ResponseEntity<>(pointService.getPoints(), HttpStatus.OK);
    }
}
