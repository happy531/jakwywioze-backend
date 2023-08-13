package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.entity.WasteType;
import com.example.jakwywiozebackend.mapper.WasteTypeMapper;
import com.example.jakwywiozebackend.repository.WasteTypeRepository;
import com.example.jakwywiozebackend.service.impl.WasteTypeServiceImpl;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class WasteTypeServiceImplTest {
    @Mock
    private WasteTypeRepository wasteTypeRepository;

    @Mock
    private WasteTypeMapper wasteTypeMapper;

    @InjectMocks
    private WasteTypeServiceImpl wasteTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWasteTypes() {
        List<WasteType> wasteTypes = List.of(new WasteType(), new WasteType());
        when(wasteTypeRepository.findAll()).thenReturn(wasteTypes);

        List<String> result = wasteTypeService.getWasteTypes();
        assertEquals(wasteTypes.size(), result.size());
        assertEquals(wasteTypes.get(0).getName(), result.get(0));
        assertEquals(wasteTypes.get(1).getName(), result.get(1));
    }

    @Test
    public void testGetWasteType() {
        Long id = 1L;
        WasteType wasteType = new WasteType();
        wasteType.setId(id);
        when(wasteTypeRepository.findById(id)).thenReturn(java.util.Optional.of(wasteType));
        WasteTypeDto wasteTypeDto = new WasteTypeDto();
        wasteTypeDto.setId(id);
        when(wasteTypeMapper.toWasteTypeDto(wasteType)).thenReturn(wasteTypeDto);

        WasteTypeDto result = wasteTypeService.getWasteType(id);
        assertEquals(id, result.getId());
    }

    @Test
    public void testCreateWasteType() {
        Long id = 1L;
        String name = "test";
        WasteTypeDto wasteTypeDto = new WasteTypeDto();
        wasteTypeDto.setId(id);
        wasteTypeDto.setName(name);
        WasteType wasteType = new WasteType();
        wasteType.setId(id);
        wasteType.setName(name);
        when(wasteTypeMapper.toWasteType(wasteTypeDto)).thenReturn(wasteType);
        when(wasteTypeRepository.findByName(name)).thenReturn(java.util.Optional.empty());
        when(wasteTypeRepository.save(wasteType)).thenReturn(wasteType);
        when(wasteTypeMapper.toWasteTypeDto(wasteType)).thenReturn(wasteTypeDto);

        WasteTypeDto result = wasteTypeService.createWasteType(wasteTypeDto);
        assertEquals(id, result.getId());
    }

    @Test
    public void testCreateWasteTypeAlreadyExists() {
        Long id = 1L;
        String name = "test";
        WasteTypeDto wasteTypeDto = new WasteTypeDto();
        wasteTypeDto.setId(id);
        wasteTypeDto.setName(name);
        WasteType wasteType = new WasteType();
        wasteType.setId(id);
        wasteType.setName(name);
        when(wasteTypeMapper.toWasteType(wasteTypeDto)).thenReturn(wasteType);
        when(wasteTypeRepository.findByName(name)).thenReturn(java.util.Optional.of(wasteType));

        assertThrows(EntityExistsException.class, () -> wasteTypeService.createWasteType(wasteTypeDto));
    }

    @Test
    public void testDeleteWasteType() {
        Long id = 1L;
        WasteType wasteType = new WasteType();
        wasteType.setId(id);
        when(wasteTypeRepository.findById(id)).thenReturn(java.util.Optional.of(wasteType));
        WasteTypeDto wasteTypeDto = new WasteTypeDto();
        wasteTypeDto.setId(id);
        when(wasteTypeMapper.toWasteTypeDto(wasteType)).thenReturn(wasteTypeDto);

        WasteTypeDto result = wasteTypeService.deleteWasteType(id);
        assertEquals(id, result.getId());
    }
}
