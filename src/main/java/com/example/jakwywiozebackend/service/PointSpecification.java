package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.entity.Point;
import org.springframework.data.jpa.domain.Specification;

public class PointSpecification {
    public static Specification<Point> getPointByCity(String city) {
        if(city == null){
            return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("city"), city);
    }
}
