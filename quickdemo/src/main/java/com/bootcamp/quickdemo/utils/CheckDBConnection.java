package com.bootcamp.quickdemo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CheckDBConnection {
    public static boolean isDatabaseConnected() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "Admin")) {
            return connection != null;
        } catch (SQLException e) {
            return false;
        }
    }
}