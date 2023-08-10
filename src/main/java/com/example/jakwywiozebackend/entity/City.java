package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class City {
    @Id
    private Long id;
    private String name;
    private String province;
    private float longitude;
    private float latitude;
    @OneToMany(mappedBy = "city")
    private List<Point> points;

}
