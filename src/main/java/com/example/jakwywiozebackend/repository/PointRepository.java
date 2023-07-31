package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.Point;
import io.micrometer.common.lang.NonNullApi;
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

//    @Query(value = "SELECT * FROM point WHERE id IN (SELECT point_id FROM point_waste ps WHERE ps.point_id IN (SELECT id FROM point WHERE city = :city) " +
//            "AND ps.waste_type_id IN (SELECT id FROM waste_type WHERE name IN :wasteTypes))", nativeQuery = true)
//    List<Point> findAllByCityAndWasteTypeIn(@Param("city") String city, @Param("wasteTypes") List<String> wasteTypes);

}
