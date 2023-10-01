package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.entity.City;
import org.springframework.data.jpa.domain.Specification;


public class CitySpecification {
    public static Specification<City> getCityBySubstring(String substring) {
        if(substring == null){
            return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), substring.toLowerCase()+"%");
    }
}
