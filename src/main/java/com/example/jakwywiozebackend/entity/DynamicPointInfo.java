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
    private User user;
    @PrimaryKeyJoinColumn
    @OneToOne
    private Point point;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private LocalDateTime startingDateTime;
    @Column
    private LocalDateTime endingDateTime;
    @Column
    @ElementCollection
    private List<String> additionalWasteTypes;
}
