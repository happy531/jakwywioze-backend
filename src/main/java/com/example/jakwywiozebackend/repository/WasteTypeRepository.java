package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.WasteType;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NonNullApi
public interface WasteTypeRepository extends JpaRepository<WasteType,Long> {

    List<WasteType> findAll();

    Optional<WasteType> findById(Long id);

}
