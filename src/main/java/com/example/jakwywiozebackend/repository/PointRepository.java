package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.Point;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NonNullApi
public interface PointRepository extends JpaRepository<Point,Long> {

    List<Point> findAll();

    Optional<Point> findById(Long id);

    @Query(value = "SELECT * from point where id IN (SELECT point_id FROM point_waste ps WHERE ps.point_id = (SELECT id FROM point WHERE city = :city) " +
            "AND ps.waste_type_id = (SELECT id FROM waste_type WHERE name IN :wasteTypes))", nativeQuery = true)
    List<Point> findAllByCityAndWasteTypeIn(@Param("city") String city, @Param("wasteTypes") List<String> wasteTypes);

    // TODO postgis
    @Query(value = "SELECT * from point where id IN (SELECT point_id FROM point_waste ps WHERE ps.waste_type_id = (SELECT id FROM waste_type WHERE name IN :wasteTypes))",
            nativeQuery = true)
    List<Point> findAllByRangeAndWasteTypeIn(@Param("wasteTypes") List<String> wasteTypes);

}
