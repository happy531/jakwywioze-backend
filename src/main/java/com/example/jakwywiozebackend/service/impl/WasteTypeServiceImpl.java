package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.entity.WasteType;
import com.example.jakwywiozebackend.mapper.WasteTypeMapper;
import com.example.jakwywiozebackend.repository.WasteTypeRepository;
import com.example.jakwywiozebackend.service.WasteTypeService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class WasteTypeServiceImpl implements WasteTypeService {
    private final WasteTypeRepository wasteTypeRepository;
    private final WasteTypeMapper wasteTypeMapper;
    @Override
    public List<String> getWasteTypes() {
        List<String> wasteTypeNames = new ArrayList<>();
        List<WasteType> wasteTypeDtos = wasteTypeRepository.findAll();
        wasteTypeDtos.forEach(wasteType -> {
            wasteTypeNames.add(wasteType.getName());
        });
        return wasteTypeNames;
    }

    @Override
    public WasteTypeDto getWasteType(Long id) {
        return wasteTypeMapper.toWasteTypeDto(wasteTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Waste type not found")));
    }

    @Override
    public WasteTypeDto createWasteType(WasteTypeDto wasteTypeDto) {
        WasteType wasteType = wasteTypeMapper.toWasteType(wasteTypeDto);
        if(wasteTypeRepository.findByName(wasteType.getName()).isPresent()){
            throw new EntityExistsException("Waste type already exists");
        }
        return wasteTypeMapper.toWasteTypeDto(wasteTypeRepository.save(wasteType));
    }

    @Override
    public WasteTypeDto deleteWasteType(Long id) {
        WasteTypeDto wasteTypeDto = getWasteType(id);
        wasteTypeRepository.delete(wasteTypeMapper.toWasteType(wasteTypeDto));
        return wasteTypeDto;
    }
}
