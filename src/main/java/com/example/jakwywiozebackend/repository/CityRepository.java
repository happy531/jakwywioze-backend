package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long>, JpaSpecificationExecutor<City> {
    List<City> findByNameContaining(String name);
}
