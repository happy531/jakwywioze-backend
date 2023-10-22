package com.example.jakwywiozebackend.utils;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader {

    private final ResourceLoader resourceLoader;
    private static final String HOST = "localhost";

    public DataLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void loadData() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + HOST + ":5432/jakwywioze", "jakwywioze", "jakwywioze")) {
            loadAndInsertPoints(connection);
            loadAndInsertCities(connection);
            loadAndInsertWasteTypes(connection);
            insertUser(connection);
            loadAndInsertPointWaste(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAndInsertPoints(Connection connection) {
        String line;
        String splitBy = ";\t";
        long id = 1L;

        try {
            Resource resource = resourceLoader.getResource("classpath:points.txt");
            InputStream inputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String query = "INSERT INTO point (id, city, image_link, lat, lon, name, opening_hours, phone_number, street, type, website, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";

            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setLong(1, id++);
                preparedStatement.setString(2, data[1]);
                preparedStatement.setString(3, data[2]);
                preparedStatement.setFloat(4, Float.parseFloat(data[3]));
                preparedStatement.setFloat(5, Float.parseFloat(data[4]));
                preparedStatement.setString(6, data[5]);
                preparedStatement.setString(7, data[6]);
                preparedStatement.setString(8, data[7]);
                preparedStatement.setString(9, data[8]);
                preparedStatement.setBoolean(10, Boolean.parseBoolean(data[9]));
                preparedStatement.setString(11, data[10]);
                preparedStatement.setString(12, data[11]);

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAndInsertCities(Connection connection) {
        String line;
        String splitBy = ";\t";
        int id = 1;

        try {
            Resource resource = resourceLoader.getResource("classpath:cities.txt");
            InputStream inputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String query = "INSERT INTO city (id, county, latitude, longitude, name, voivodeship) VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";

            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setInt(1, id++);
                preparedStatement.setString(2, null);
                preparedStatement.setFloat(3, Float.parseFloat(data[1]));
                preparedStatement.setFloat(4, Float.parseFloat(data[2]));
                preparedStatement.setString(5, data[0]);
                preparedStatement.setString(6, null);

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAndInsertWasteTypes(Connection connection) {
        String line;
        String splitBy = ";";
        int id = 1;

        try {
            Resource resource = resourceLoader.getResource("classpath:waste_types.txt");
            InputStream inputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String query = "INSERT INTO waste_type (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);

                preparedStatement.setInt(1, id++);
                preparedStatement.setString(2, data[0]);

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertUser(Connection connection) {
        try {
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

    private void loadAndInsertPointWaste(Connection connection) {
        String line;
        String splitBy = ";";

        try {
            // Check if the unique index exists, if not create it
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getIndexInfo(null, null, "point_waste", true, false);
            boolean indexExists = false;

            while (resultSet.next()) {
                String indexName = resultSet.getString("INDEX_NAME");
                if ("idx_point_waste".equals(indexName)) {
                    indexExists = true;
                    break;
                }
            }

            if (!indexExists) {
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("CREATE UNIQUE INDEX idx_point_waste ON point_waste (point_id, waste_type_id)");
                }
            }

            Resource resource = resourceLoader.getResource("classpath:point_waste.txt");
            InputStream inputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String query = "INSERT INTO point_waste (point_id, waste_type_id) VALUES (?, ?) ON CONFLICT (point_id, waste_type_id) DO NOTHING";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);

                preparedStatement.setInt(1, Integer.parseInt(data[0]));
                preparedStatement.setInt(2, Integer.parseInt(data[1]));

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}