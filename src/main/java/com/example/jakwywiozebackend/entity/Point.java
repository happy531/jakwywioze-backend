package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @Column
    private String street;
    @Column
    private String zipcode;
    @Column
    private String phoneNumber;
    @Column
    private String website;
    @Column
    private String imageLink;
    @Column
    private Boolean type;
    @PrimaryKeyJoinColumn
    @OneToOne
    private DynamicPointInfo dynamicPointInfo;
    @ManyToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinTable(
            name = "point_waste",
            joinColumns = @JoinColumn(name = "point_id"),
            inverseJoinColumns = @JoinColumn(name = "waste_type_id")
    )
    private List<WasteType> wasteTypes;
    @Column
    @OneToMany
    private List<Comment> comments;
}
