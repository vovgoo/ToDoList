package com.example.test;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class DatabaseHandler {

    private final Connection connection;

    public DatabaseHandler(Connection conn) {
        this.connection = conn;
    }

    public void createTasksTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tasks (id SERIAL PRIMARY KEY, name TEXT)")) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Task> getAllTask() {
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Task task = new Task(rs.getString("name"), rs.getInt("id"));
                tasks.add(task);
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return tasks;
    }

    public Task insertTask(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tasks (name) VALUES (?) RETURNING id")) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new Task(name, rs.getInt(1));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeTask(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateTask(String name, int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET name = ? WHERE id = ?")) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
