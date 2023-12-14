package com.example.test.controllers;

import com.example.test.db.DatabaseHandler;
import com.example.test.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.sql.Connection;
import java.sql.DriverManager;

public class HelloController {

    @FXML
    private FlowPane tasks;

    @FXML
    private TextField taskField;

    public static final int TASK_NAME_MAX_LENGTH = 26;

    private DatabaseHandler database;

    @FXML
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
            this.database = new DatabaseHandler(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        database.createTasksTable();
        for (Task task : database.getAllTask()) {
            TaskController taskField = new TaskController(task.getName(), task.getId(), database);
            tasks.getChildren().add(taskField);
        }
    }

    @FXML
    protected void addTasksHandler() {
        String taskName = taskField.getText();
        if (!taskName.isBlank() && taskName.length() <= TASK_NAME_MAX_LENGTH) {
            Task newTask = database.insertTask(taskName);
            TaskController task = new TaskController(newTask.getName(), newTask.getId(), database);
            tasks.getChildren().add(task);
            this.taskField.setText("");
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            if (taskName.length() > TASK_NAME_MAX_LENGTH) {
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task is too long!");
                alert.show();
            } else if (taskName.isBlank()) {
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task input field is empty!");
                alert.show();
            }
        }
    }
}