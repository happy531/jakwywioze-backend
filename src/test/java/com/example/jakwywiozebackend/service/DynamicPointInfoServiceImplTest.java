package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.DynamicPointInfoDto;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import com.example.jakwywiozebackend.mapper.DynamicPointInfoMapper;
import com.example.jakwywiozebackend.repository.DynamicPointInfoRepository;
import com.example.jakwywiozebackend.service.impl.DynamicPointInfoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class DynamicPointInfoServiceImplTest {
    @Mock
    private DynamicPointInfoRepository dynamicPointInfoRepository;

    @Mock
    private DynamicPointInfoMapper dynamicPointInfoMapper;

    @InjectMocks
    private DynamicPointInfoServiceImpl dynamicPointInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDynamicPointInfos() {
        List<DynamicPointInfo> dynamicPointInfos = List.of(new DynamicPointInfo(), new DynamicPointInfo());
        when(dynamicPointInfoRepository.findAll()).thenReturn(dynamicPointInfos);
        List<DynamicPointInfoDto> dynamicPointInfoDtos = List.of(new DynamicPointInfoDto(), new DynamicPointInfoDto());
        when(dynamicPointInfoMapper.toDynamicPointInfoDtoList(dynamicPointInfos)).thenReturn(dynamicPointInfoDtos);

        List<DynamicPointInfoDto> result = dynamicPointInfoService.getDynamicPointInfos();
        assertEquals(dynamicPointInfoDtos.size(), result.size());
    }

    @Test
    public void testGetDynamicPointInfo() {
        Long id = 1L;
        DynamicPointInfo dynamicPointInfo = new DynamicPointInfo();
        dynamicPointInfo.setId(id);
        when(dynamicPointInfoRepository.findById(id)).thenReturn(java.util.Optional.of(dynamicPointInfo));
        DynamicPointInfoDto dynamicPointInfoDto = new DynamicPointInfoDto();
        dynamicPointInfoDto.setId(id);
        when(dynamicPointInfoMapper.toDynamicPointInfoDto(dynamicPointInfo)).thenReturn(dynamicPointInfoDto);

        DynamicPointInfoDto result = dynamicPointInfoService.getDynamicPointInfo(id);
        assertEquals(id, result.getId());
    }

    @Test
    public void testCreateDynamicPointInfo() {
        Long id = 1L;
        DynamicPointInfoDto dynamicPointInfoDto = new DynamicPointInfoDto();
        dynamicPointInfoDto.setId(id);
        DynamicPointInfo dynamicPointInfo = new DynamicPointInfo();
        dynamicPointInfo.setId(id);
        when(dynamicPointInfoMapper.toDynamicPointInfo(dynamicPointInfoDto)).thenReturn(dynamicPointInfo);
        when(dynamicPointInfoRepository.save(dynamicPointInfo)).thenReturn(dynamicPointInfo);
        when(dynamicPointInfoMapper.toDynamicPointInfoDto(dynamicPointInfo)).thenReturn(dynamicPointInfoDto);

        DynamicPointInfoDto result = dynamicPointInfoService.createDynamicPointInfo(dynamicPointInfoDto);
        assertEquals(id, result.getId());
    }
}
