package com.example.jakwywiozebackend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UtilsServiceImplTest {

    @Autowired
    private UtilsService utilsService;

    @Test
    public void testCalculateRange() {
        double lat1 = 51.5074;
        double lon1 = 0.1278;
        double lat2 = 48.8566;
        double lon2 = 2.3522;
        double expected = 334.5761;
        double actual = utilsService.calculateRange(lat1, lon1, lat2, lon2);
        assertEquals(expected, actual, 0.0001);
    }
}
