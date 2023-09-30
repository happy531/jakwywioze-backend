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
        String line = "";
        String splitBy = ";";
        try {
            Resource resource = resourceLoader.getResource("classpath:points.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                insertData(data);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertData(String[] data) {
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
}
