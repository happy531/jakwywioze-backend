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
        loadWasteTypes();
        insertUser();
    }

    private void loadPoints() {
        String line = "";
        String splitBy = ";\t";
        Long id = 1L;
        try {
            Resource resource = resourceLoader.getResource("classpath:points.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                insertPoints(id, data);
                id++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertPoints(Long id, String[] data) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jakwywioze", "jakwywioze", "jakwywioze")) {
            String query = "INSERT INTO point (id, name, street, zipcode, city, lat, lon, phone_number, website, image_link, opening_hours, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < 9; i++) {
                if (i == 4 || i == 5) { // If the column is 'lon' or 'lat'
                    try {
                        preparedStatement.setLong(1, id);
                        float value = Float.parseFloat(data[i]);
                        preparedStatement.setFloat(i + 2, value);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing float value for data[" + i + "]: " + data[i]);
                        e.printStackTrace();
                    }
                } else {
                    preparedStatement.setString(i + 2, data[i]);
                }
            }
            preparedStatement.setNull(11, java.sql.Types.VARCHAR);
            preparedStatement.setNull(12, Types.BOOLEAN);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCities() {
        String line = "";
        String splitBy = ";\t";
        int id = 1;
        try {
            Resource resource = resourceLoader.getResource("classpath:cities.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                insertCities(id, data);
                id++;
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
            preparedStatement.setInt(1, id);
            for (int i = 0; i < 5; i++) {
                if (i == 3 || i == 4) {
                    try {
                        float value = Float.parseFloat(data[i]);
                        preparedStatement.setFloat(i + 2, value);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing float value for data[" + i + "]: " + data[i]);
                        e.printStackTrace();
                    }
                } else {
                    preparedStatement.setString(i + 2, data[i]);
                }
            }

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWasteTypes() {
        String line = "";
        String splitBy = ";";
        int id = 1;
        try {
            Resource resource = resourceLoader.getResource("classpath:waste_types.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                insertWasteType(id, data);
                id++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertWasteType(int id, String[] data) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jakwywioze", "jakwywioze", "jakwywioze")) {
            String query = "INSERT INTO waste_type (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, data[0]);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertUser() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jakwywioze", "jakwywioze", "jakwywioze")) {
            String query = "INSERT INTO \"user\" (id, username, password, role) VALUES (?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, 0);
            preparedStatement.setString(2, "admin");
            preparedStatement.setString(3, "$2a$12$QcRYVci5qvmVlPMrZynv4.CuolM6g1IhGBgs690Ga6fQRMVP/GMue");
            preparedStatement.setString(4, "ADMIN");

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}