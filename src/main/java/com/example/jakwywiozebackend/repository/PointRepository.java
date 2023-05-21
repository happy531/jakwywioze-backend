package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.Point;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PointRepository extends JpaRepository<Point,Long> {
    @NonNull
    List<Point> findAll();

}
