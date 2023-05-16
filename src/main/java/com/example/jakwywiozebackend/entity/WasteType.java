package com.example.jakwywiozebackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class WasteType {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    @ManyToMany
    private List<Point> points;
}
