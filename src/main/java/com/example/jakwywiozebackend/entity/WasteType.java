package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class WasteType {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "entity_id_seq"
    )
    @SequenceGenerator(
            name = "entity_id_seq",
            sequenceName = "global_id_sequence",
            allocationSize = 1
    )
    @Column(
            unique = true,
            updatable = false,
            nullable = false
    )
    private Long id;
    @Column
    private String name;
    @Column
    @JsonIgnore
    @ManyToMany(mappedBy = "wasteTypes")
    private List<Point> points;
}
