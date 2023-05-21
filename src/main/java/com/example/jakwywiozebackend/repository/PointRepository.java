package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.dto.PointDto;
import com.example.jakwywiozebackend.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<PointDto,Long> {
    List<PointDto> findAll();
}
