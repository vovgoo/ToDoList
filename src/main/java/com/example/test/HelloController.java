package com.example.test;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {

    @FXML
    private FlowPane tasks;

    @FXML
    private TextField task_field;

    private DatabaseHandler database;

    @FXML
    public void initialize() {
        this.database = new DatabaseHandler("javafx_todo", "postgres", "postgres");
        database.createTable();
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tasks");
            while (rs.next()) {
                TaskController task = new TaskController(rs.getString("task"), rs.getInt("id"), database);
                tasks.getChildren().add(task);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    protected void addTasksHandler() {
        if (!task_field.getText().trim().isEmpty() && task_field.getText().length() <= 26) {
            TaskController task = new TaskController(task_field.getText(), database.getMaxId() + 1, database);
            tasks.getChildren().add(task);
            database.insertTask(task_field.getText());
            this.task_field.setText("");
        }
        else {
            Alert alert = new Alert(AlertType.INFORMATION);
            if (task_field.getText().length() > 26) {
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task is too long!");
                alert.show();
            } else if (task_field.getText().trim().isEmpty()) {
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task input field is empty!");
                alert.show();
            }
        }
    }
}