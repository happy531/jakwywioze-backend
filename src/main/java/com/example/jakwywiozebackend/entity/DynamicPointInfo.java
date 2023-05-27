package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    User user;
    @OneToOne
    Point point;
    String city;
    String street;
    LocalDateTime startingDateTime;
    LocalDateTime endingDateTime;
    @ElementCollection
    List<String> additionalWasteTypes;
}
