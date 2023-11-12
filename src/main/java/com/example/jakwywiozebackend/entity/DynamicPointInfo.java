package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table
public class DynamicPointInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @OneToOne(mappedBy = "dynamicPointInfo")
    private Point point;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    @ElementCollection
    private List<String> additionalWasteTypes;
}
