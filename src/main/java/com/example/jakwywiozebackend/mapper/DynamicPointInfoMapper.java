package com.example.jakwywiozebackend.mapper;
import com.example.jakwywiozebackend.dto.DynamicPointInfoDto;
import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface DynamicPointInfoMapper {
    @Mapping(source = "user", target = "user.id")
    DynamicPointInfo toDynamicPointInfo(DynamicPointInfoDto dynamicPointInfoDto);
    @Mapping(source = "user.id", target = "user")
    DynamicPointInfoDto toDynamicPointInfoDto(DynamicPointInfo dynamicPointInfo);
    List<DynamicPointInfo> toDynamicPointInfoList(List<DynamicPointInfoDto> dynamicPointInfoDtos);
    List<DynamicPointInfoDto> toDynamicPointInfoDtoList(List<DynamicPointInfo> points);
}
