package com.example.jakwywiozebackend.mapper;

import com.example.jakwywiozebackend.dto.WasteTypeDto;
import com.example.jakwywiozebackend.entity.WasteType;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface WasteTypeMapper {
    WasteType toWasteType(WasteTypeDto wasteTypeDto);
    WasteTypeDto toWasteTypeDto(WasteType wasteType);
    List<WasteType> toWasteTypeList(List<WasteTypeDto> wasteTypeDtos);
    List<WasteTypeDto> toWasteTypeDtoList(List<WasteType> wasteTypes);
    WasteTypeDto map(String name);
    List<WasteTypeDto> toWasteTypeDtoListFromStringList(List<String> wasteList);
}
