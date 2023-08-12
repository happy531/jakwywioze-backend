package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.entity.Point;
import com.example.jakwywiozebackend.entity.WasteType;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PointSpecification {
    public static Specification<Point> getPointByCity(String city) {
        if(city == null){
            return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("city"), city);
    }

    public static Specification<Point> getPointByWasteTypes(List<String> wasteTypes) {
        if(wasteTypes == null || wasteTypes.isEmpty()){
            return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        }
        return (root, query, criteriaBuilder) -> {
            final Path<WasteType> wasteTypePath = root.get("wasteTypes").get("name");
            return wasteTypePath.in(wasteTypes);
        };

    }
}
