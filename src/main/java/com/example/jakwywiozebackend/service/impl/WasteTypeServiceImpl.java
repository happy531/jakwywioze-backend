package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.entity.WasteType;
import com.example.jakwywiozebackend.mapper.WasteTypeMapper;
import com.example.jakwywiozebackend.repository.WasteTypeRepository;
import com.example.jakwywiozebackend.service.WasteTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class WasteTypeServiceImpl implements WasteTypeService {
    private final WasteTypeRepository wasteTypeRepository;
    private final WasteTypeMapper wasteTypeMapper;
    @Override
    public List<WasteTypeDto> getWasteTypes() {
        return wasteTypeMapper.toWasteTypeDtoList(wasteTypeRepository.findAll());
    }

    @Override
    public WasteTypeDto getWasteType(Long id) {
        return wasteTypeMapper.toWasteTypeDto(wasteTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("null waste type")));
    }

    @Override
    public WasteTypeDto createWasteType(WasteTypeDto wasteTypeDto) {
        WasteType wasteType = wasteTypeMapper.toWasteType(wasteTypeDto);
        return wasteTypeMapper.toWasteTypeDto(wasteTypeRepository.save(wasteType));
    }
}
