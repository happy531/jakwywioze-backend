package com.example.jakwywiozebackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;
@Data
@Getter
@Setter
public class DynamicPointInfoDto {

    private Long id;
    @Length(max = 250, message = "Description too long")
    private String description;
    private Long user;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> additionalWasteTypes;
}
