package com.example.test;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private final Connection connection;

    public DatabaseHandler(Connection conn) {
        this.connection = conn;
    }

    public void createTasksTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tasks (id SERIAL PRIMARY KEY, task VARCHAR(255));")) {
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Task task = new Task(rs.getString("task"), rs.getInt("id"));
                tasks.add(task);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return tasks;
    }

    public Task insertTask(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tasks (task) VALUES (?) RETURNING id;")) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Task(name, rs.getInt(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return new Task("None", -1);
    }

    public void removeTask(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateTask(String name, int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET task = ? WHERE id = ?")) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getNameById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT task FROM tasks WHERE id = ?")){
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return "None";
    }
}
