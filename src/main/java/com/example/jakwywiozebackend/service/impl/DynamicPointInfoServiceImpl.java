package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.DynamicPointInfoDto;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.mapper.DynamicPointInfoMapper;
import com.example.jakwywiozebackend.mapper.PointMapper;
import com.example.jakwywiozebackend.repository.DynamicPointInfoRepository;
import com.example.jakwywiozebackend.repository.PointRepository;
import com.example.jakwywiozebackend.service.DynamicPointInfoService;
import com.example.jakwywiozebackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DynamicPointInfoServiceImpl implements DynamicPointInfoService {

    private final DynamicPointInfoRepository dynamicPointInfoRepository;
    private final DynamicPointInfoMapper dynamicPointInfoMapper;
    @Override
    public List<DynamicPointInfoDto> getDynamicPointInfos() {
        return dynamicPointInfoMapper.toDynamicPointInfoDtoList(dynamicPointInfoRepository.findAll());
    }

    @Override
    public DynamicPointInfoDto getDynamicPointInfo(Long id) {
        return dynamicPointInfoMapper.toDynamicPointInfoDto(dynamicPointInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("null point")));
    }

    @Override
    public DynamicPointInfoDto createDynamicPointInfo(DynamicPointInfoDto dynamicPointInfoDto) {
        DynamicPointInfo dynamicPointInfo = dynamicPointInfoMapper.toDynamicPointInfo(dynamicPointInfoDto);
        return dynamicPointInfoMapper.toDynamicPointInfoDto(dynamicPointInfoRepository.save(dynamicPointInfo));
    }
}
