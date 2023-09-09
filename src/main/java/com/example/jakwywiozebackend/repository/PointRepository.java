package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.Point;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NonNullApi
public interface PointRepository extends JpaRepository<Point,Long> {

    List<Point> findAll();

    Optional<Point> findById(Long id);

    List<Point> findAll(Specification<Point> spec);
    Page<Point> findAll(Specification<Point> spec, Pageable pageable);

}
