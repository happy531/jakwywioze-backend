package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.DynamicPointInfoDto;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.service.DynamicPointInfoService;
import com.example.jakwywiozebackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamic-points")
@RequiredArgsConstructor
public class DynamicPointInfoController {
    private final DynamicPointInfoService dynamicPointInfoService;

    @PostMapping
    public ResponseEntity<DynamicPointInfoDto> createDynamicPointInfo(@RequestBody DynamicPointInfoDto dynamicPointInfoDto){
        return new ResponseEntity<>(dynamicPointInfoService.createDynamicPointInfo(dynamicPointInfoDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DynamicPointInfoDto> getDynamicPointInfo(@PathVariable Long id){
        return new ResponseEntity<>(dynamicPointInfoService.getDynamicPointInfo(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DynamicPointInfoDto>> getAllDynamicPointInfos(){
        return new ResponseEntity<>(dynamicPointInfoService.getDynamicPointInfos(), HttpStatus.OK);
    }
}
