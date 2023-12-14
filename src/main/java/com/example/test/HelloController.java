package com.example.test;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class HelloController {

    @FXML
    private FlowPane tasks;

    @FXML
    private TextField taskField;

    public static final int TASk_SIZE = 26;

    @FXML
    public void initialize() {
        HelloApplication.database.createTasksTable();
        for (Task task : HelloApplication.database.getAllTask()) {
            TaskController taskField = new TaskController(task.getName(), task.getId());
            tasks.getChildren().add(taskField);
        }
    }

    @FXML
    protected void addTasksHandler() {
        String taskName = taskField.getText();
        if (!taskName.isBlank() && taskName.length() <= TASk_SIZE) {
            Task newTask = HelloApplication.database.insertTask(taskName);
            TaskController task = new TaskController(newTask.getName(), newTask.getId());
            tasks.getChildren().add(task);
            this.taskField.setText("");
        }
        else {
            Alert alert = new Alert(AlertType.INFORMATION);
            if (taskName.length() > TASk_SIZE) {
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