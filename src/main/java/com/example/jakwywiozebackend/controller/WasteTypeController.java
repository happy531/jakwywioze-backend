package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.service.WasteTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waste-types")
@RequiredArgsConstructor
public class WasteTypeController {
    private final WasteTypeService wasteTypeService;

    @PostMapping
    public ResponseEntity<WasteTypeDto> createWasteType(@RequestBody WasteTypeDto wasteTypeDto){
        return new ResponseEntity<>(wasteTypeService.createWasteType(wasteTypeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteTypeDto> getWasteType(@RequestBody @PathVariable Long id){
        return new ResponseEntity<>(wasteTypeService.getWasteType(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WasteTypeDto>> getAllWasteTypes(){
        return new ResponseEntity<>(wasteTypeService.getWasteTypes(), HttpStatus.OK);
    }
}
