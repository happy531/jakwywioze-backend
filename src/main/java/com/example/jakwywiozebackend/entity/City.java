package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table
public class City {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String province;
    @Column
    private float longitude;
    @Column
    private float latitude;

}
