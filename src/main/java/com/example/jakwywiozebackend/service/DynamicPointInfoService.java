package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.DynamicPointInfoDto;
import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;

import java.util.List;

public interface DynamicPointInfoService {
    List<DynamicPointInfoDto> getDynamicPointInfos();
    DynamicPointInfoDto getDynamicPointInfo(Long id);
    DynamicPointInfoDto createDynamicPointInfo(DynamicPointInfoDto dynamicPointInfoDto);
    List<DynamicPointInfo> findDynamicPointsAssignedToUser(Long userId);
}
