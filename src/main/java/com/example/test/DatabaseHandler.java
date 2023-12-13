package com.example.test;

import java.sql.*;

public class DatabaseHandler {

    private final Connection connection;

    public DatabaseHandler(Connection _conn) {
        this.connection = _conn;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void createTasksTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tasks (id SERIAL PRIMARY KEY, task VARCHAR(255));");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void insertTask(String name) {
        String sql = "INSERT INTO tasks (task) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error inserting task into the database", ex);
        }
    }

    public int getMaxId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) FROM tasks");
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return -1;
    }

    public String getNameById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT task FROM tasks WHERE id = " + id);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return "None";
    }
}
