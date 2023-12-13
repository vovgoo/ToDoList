package com.example.test;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.sql.*;

public class HelloController {

    @FXML
    private FlowPane tasks;

    @FXML
    private TextField taskField;

    private DatabaseHandler database;

    @FXML
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "javafx_todo", "postgres", "postgres");
            this.database = new DatabaseHandler(conn);
            database.createTasksTable();
            for (Task task : database.getAllTask()) {
                TaskController taskField = new TaskController(task.getName(), task.getId(), database);
                tasks.getChildren().add(taskField);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void addTasksHandler() {
        if (!taskField.getText().trim().isEmpty() && taskField.getText().length() <= 26) {
            database.insertTask(taskField.getText());
            TaskController task = new TaskController(taskField.getText(), database.getMaxId(), database);
            tasks.getChildren().add(task);
            this.taskField.setText("");
        }
        else {
            Alert alert = new Alert(AlertType.INFORMATION);
            if (taskField.getText().length() > 26) {
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task is too long!");
                alert.show();
            } else if (taskField.getText().trim().isEmpty()) {
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task input field is empty!");
                alert.show();
            }
        }
    }
}