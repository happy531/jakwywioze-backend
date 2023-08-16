package com.example.jakwywiozebackend.utils;

import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.entity.Point;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /*
        Earth's radius in kilometers
    */
    private static final double EARTH_RADIUS = 6371.0;

    private static double calculateRange(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static List<Point> filterPointsByRange(List<Point> points, CityDto city, int range) {
        List<Point> pointsInRange = new ArrayList<>();
        for (Point point : points) {
            if (calculateRange(point.getLat(), point.getLon(), city.getLatitude(), city.getLongitude()) <= range) {
                pointsInRange.add(point);
            }
        }
        return pointsInRange;
    }
}