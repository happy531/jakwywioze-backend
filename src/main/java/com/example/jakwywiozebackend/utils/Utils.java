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

    public static float distanceFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (float) (earthRadius * c);
    }
}