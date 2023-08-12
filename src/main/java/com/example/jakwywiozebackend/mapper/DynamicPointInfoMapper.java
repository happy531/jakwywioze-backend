package com.example.jakwywiozebackend.mapper;
import com.example.jakwywiozebackend.dto.DynamicPointInfoDto;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface DynamicPointInfoMapper {
    DynamicPointInfo toDynamicPointInfo(DynamicPointInfoDto dynamicPointInfoDto);
    DynamicPointInfoDto toDynamicPointInfoDto(DynamicPointInfo dynamicPointInfo);
    List<DynamicPointInfo> toDynamicPointInfoList(List<DynamicPointInfoDto> dynamicPointInfoDtos);
    List<DynamicPointInfoDto> toDynamicPointInfoDtoList(List<DynamicPointInfo> points);
}
