package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {
    private PointService pointService;
}
