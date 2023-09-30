package com.example.jakwywiozebackend.utils;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Types;

@Component
public class DataLoader {

    private final ResourceLoader resourceLoader;

    public DataLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void loadData() {
        loadPoints();
        loadCities();
    }

    private void loadPoints() {
        String line = "";
        String splitBy = ";";
        try {
            Resource resource = resourceLoader.getResource("classpath:points.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                insertPoints(data);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertPoints(String[] data) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jakwywioze", "jakwywioze", "jakwywioze")) {
            String query = "INSERT INTO point (name, street, zipcode, city, lon, lat, phone_number, website, image_link, opening_hours, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < 9; i++) {
                if (i == 4 || i == 5) { // If the column is 'lon' or 'lat'
                    try {
                        float value = Float.parseFloat(data[i]);
                        preparedStatement.setFloat(i + 1, value);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing float value for data[" + i + "]: " + data[i]);
                        e.printStackTrace();
                    }
                } else {
                    preparedStatement.setString(i + 1, data[i]);
                }
            }
            preparedStatement.setNull(10, java.sql.Types.VARCHAR);
            preparedStatement.setNull(11, Types.BOOLEAN);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCities() {
        String line = "";
        String splitBy = ";";
        int id = 1; // Initialize the id counter
        try {
            Resource resource = resourceLoader.getResource("classpath:cities.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                insertCities(id, data);
                id++; // Increment the id for each city
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertCities(int id, String[] data) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jakwywioze", "jakwywioze", "jakwywioze")) {
            String query = "INSERT INTO city (id, name, voivodeship, county, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id); // Set the id value
            for (int i = 0; i < 5; i++) {
                if (i == 3 || i == 4) { // If the column is 'latitude' or 'longitude'
                    try {
                        float value = Float.parseFloat(data[i]);
                        preparedStatement.setFloat(i + 2, value); // Note the index is now i + 2
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing float value for data[" + i + "]: " + data[i]);
                        e.printStackTrace();
                    }
                } else {
                    preparedStatement.setString(i + 2, data[i]); // Note the index is now i + 2
                }
            }

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}