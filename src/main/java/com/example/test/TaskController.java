package com.example.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Statement;


public class TaskController extends HBox {
    @FXML
    private TextField task_name;

    @FXML
    private Button update_button;

    private final FunctionsDB database;

    private final int id;

    public TaskController(String task_text, int _id, FunctionsDB _database) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("custom-controller.fxml"));
        try {
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            task_name.setText(task_text);
            update_button.setVisible(false);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.id = _id;
        this.database = _database;

        task_name.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (task_name.getText().length() >= 26) {
                event.consume();
            }
        });

        task_name.textProperty().addListener((obs, oldVal, newVal) -> update_button.setVisible(!database.getNameById(id).equals(task_name.getText())));

    }

    @FXML
    protected void deleteTaskHandler() {
        try {
            Statement statement = database.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM tasks WHERE id = " + id);
            FlowPane parentContainer = (FlowPane) getParent();
            parentContainer.getChildren().remove(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    protected void updateTaskHandler() {
        try {
            Statement statement = database.getConnection().createStatement();
            statement.executeUpdate("UPDATE tasks SET task = '" + task_name.getText() + "' WHERE id = " + id);
            update_button.setVisible(false);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
