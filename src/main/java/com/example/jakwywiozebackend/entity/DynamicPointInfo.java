package com.example.jakwywiozebackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DynamicPointInfo {
    @Id
    private Long id;
}
