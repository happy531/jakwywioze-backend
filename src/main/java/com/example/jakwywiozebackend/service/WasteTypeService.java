package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.WasteTypeDto;

import java.util.List;

public interface WasteTypeService {
    List<WasteTypeDto> getWasteTypes();
    WasteTypeDto getWasteType(Long id);
    WasteTypeDto createWasteType(WasteTypeDto wasteTypeDto);
    WasteTypeDto deleteWasteType(Long id);
}
