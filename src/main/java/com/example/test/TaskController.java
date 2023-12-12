package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;


public class TaskController extends HBox {
    @FXML
    private TextField task_name;

    @FXML
    private Button update_button;

    private int id = -1;

    private final Connection conn;

    private final FunctionsDB db;

    public TaskController(String task_text, int _id, Connection _conn, FunctionsDB _db) {
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
        this.conn = _conn;
        this.db = _db;

        task_name.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (task_name.getText().length() >= 26) {
                event.consume();
            }
        });

        task_name.textProperty().addListener((obs, oldVal, newVal) -> {
            update_button.setVisible(!db.getNameById(conn, id).equals(task_name.getText()));
        });

    }

    @FXML
    protected void deleteTaskHandler(ActionEvent event) {
        try {
            String query = "DELETE FROM tasks WHERE id = " + id;
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Task removed successful.");
            FlowPane parentContainer = (FlowPane) getParent();
            parentContainer.getChildren().remove(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    protected void updateTaskHandler(ActionEvent event) {
        try {
            String query = "UPDATE tasks SET task = '" + task_name.getText() + "' WHERE id = " + id;
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            update_button.setVisible(false);
            System.out.println("Data update successful");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
