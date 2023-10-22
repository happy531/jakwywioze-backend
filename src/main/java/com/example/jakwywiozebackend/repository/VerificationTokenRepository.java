package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.dto.VerificationTokenDto;
import com.example.jakwywiozebackend.entity.City;
import com.example.jakwywiozebackend.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long>, JpaSpecificationExecutor<VerificationToken> {
    VerificationToken findByToken(String name);
}
