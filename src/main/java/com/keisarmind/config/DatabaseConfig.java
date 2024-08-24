package com.keisarmind.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) { // Check if connection is null or closed
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connection successful.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error connecting to the database", ex);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error closing the database connection", ex);
        }
    }
}



