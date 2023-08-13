package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.service.impl.UtilsServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsServiceImplTest {

    @Test
    public void testCalculateRange() {
        double lat1 = 52.2296756;
        double lon1 = 21.0122287;
        double lat2 = 52.406374;
        double lon2 = 16.9251681;
        double expected = 278.4581750754199;

        double result = UtilsServiceImpl.calculateRange(lat1, lon1, lat2, lon2);
        assertEquals(expected, result, 0.0001);
    }
}
