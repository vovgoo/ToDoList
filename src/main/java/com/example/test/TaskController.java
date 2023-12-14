package com.example.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class TaskController extends HBox {
    @FXML
    private TextField taskName;

    @FXML
    private Button updateButton;

    private final DatabaseHandler database;

    private int id;

    private String originalName;

    public TaskController(String taskText, int id, DatabaseHandler database) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("custom-controller.fxml"));
        try {
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            taskName.setText(taskText);
            updateButton.setVisible(false);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.id = id;
        this.database = database;
        this.originalName = taskText;

        taskName.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (taskName.getText().length() >= HelloController.taskSize) {
                event.consume();
            }
        });

        taskName.textProperty().addListener((obs, oldVal, newVal) -> updateButton.setVisible(!originalName.equals(taskName.getText())));

    }

    @FXML
    protected void deleteTaskHandler() {
        try {
            database.removeTask(this.id);
            FlowPane parentContainer = (FlowPane) getParent();
            parentContainer.getChildren().remove(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    protected void updateTaskHandler() {
        try {
            String taskText = taskName.getText();
            database.updateTask(taskText, this.id);
            this.originalName = taskText;
            updateButton.setVisible(false);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
