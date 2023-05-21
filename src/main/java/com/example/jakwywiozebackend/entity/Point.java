package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String openingHours;
    @Column
    private float lon;
    @Column
    private float lat;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private Boolean type;
    @PrimaryKeyJoinColumn
    @OneToOne
    private DynamicPointInfo dynamicPointInfo;
    @Column
    @ManyToMany
    private List<WasteType> wasteTypes;
    @Column
    @OneToMany
    private List<Comment> comments;
}
