package com.example.jakwywiozebackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Data
@Getter
@Setter
public class DynamicPointInfoDto {

    private Long id;
    private Long user;
    private Long point;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> additionalWasteTypes;
}
