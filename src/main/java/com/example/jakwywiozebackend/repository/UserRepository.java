package com.example.jakwywiozebackend.repository;

import com.example.jakwywiozebackend.entity.User;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NonNullApi
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

}
