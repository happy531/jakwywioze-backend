package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.DynamicPointInfo;
import com.example.jakwywiozebackend.entity.Point;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NonNullApi
public interface DynamicPointInfoRepository extends JpaRepository<DynamicPointInfo,Long> {

    List<DynamicPointInfo> findAll();

    Optional<DynamicPointInfo> findById(Long id);

}
