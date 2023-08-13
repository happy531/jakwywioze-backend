package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {
    List<City> findByNameContaining(String name);
}
