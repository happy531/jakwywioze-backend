package com.example.jakwywiozebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "`User`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String role;
    @Column
    @OneToMany
    private List<DynamicPointInfo> dynamicPointInfoList;
    @Column
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @Column
    private boolean active = false;
}
