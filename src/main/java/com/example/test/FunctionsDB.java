package com.example.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FunctionsDB {
    public Connection connectToDB(String dbname, String user, String password)
    {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, password);
            System.out.println("Connection to the database.");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error connecting to the database: " + ex.getMessage());
            throw new RuntimeException("Error connecting to the database.", ex);
        }
    }

    public void createTable(Connection conn) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS tasks (id SERIAL PRIMARY KEY, task VARCHAR(255));";
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table with tasks created!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void insertTask(Connection conn, String task) {
        try {
            String query = "INSERT INTO tasks (task) VALUES ('" + task + "')";
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data insert to tables.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getMaxId(Connection conn) {
        try {
            String query = "SELECT MAX(id) FROM tasks";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public String getNameById(Connection conn, int id) {
        try {
            String query = "SELECT task FROM tasks WHERE id = " + id;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "None";
    }
}
