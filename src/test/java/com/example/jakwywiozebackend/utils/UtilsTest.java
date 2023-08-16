package com.example.jakwywiozebackend.utils;

import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.entity.Point;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test
    public void testCalculateRange() throws Exception {
        Method calculateRangeMethod = Utils.class.getDeclaredMethod("calculateRange", double.class, double.class, double.class, double.class);
        calculateRangeMethod.setAccessible(true);

        double lat1 = 50.0;
        double lon1 = 20.0;
        double lat2 = 51.0;
        double lon2 = 21.0;

        double result = (double) calculateRangeMethod.invoke(null, lat1, lon1, lat2, lon2);

        assertEquals(131.7, result, 0.5);
    }

    @Test
    public void testFilterPointsByRange() {
        List<Point> points = new ArrayList<>();
        Point point1 = new Point();
        point1.setId(1L);
        point1.setLat(50.0f);
        point1.setLon(20.0f);
        points.add(point1);

        Point point2 = new Point();
        point2.setId(2L);
        point2.setLat(51.0f);
        point2.setLon(21.0f);
        points.add(point2);

        Point point3 = new Point();
        point3.setId(3L);
        point3.setLat(52.0f);
        point3.setLon(22.0f);
        points.add(point3);

        CityDto city = new CityDto();
        city.setLatitude(50.0f);
        city.setLongitude(20.0f);

        List<Point> filteredPoints = Utils.filterPointsByRange(points, city, 100);

        assertEquals(1, filteredPoints.size());
        assertEquals(1L, filteredPoints.get(0).getId());
    }
}
