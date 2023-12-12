package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {

    private final FunctionsDB db = new FunctionsDB();
    private final Connection conn = db.connectToDB("javafx_todo", "postgres", "postgres");

    @FXML
    private FlowPane tasks;

    @FXML
    private Button add_tasks;

    @FXML
    private Label task_text;

    @FXML
    private TextField task_field;

    @FXML
    public void initialize()
    {
        db.createTable(conn);
        Statement statement;
        ResultSet rs = null;
        try
        {
            String query = "SELECT * FROM tasks";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                TaskController task = new TaskController(rs.getString("task"), rs.getInt("id"), conn, db);
                tasks.getChildren().add(task);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    protected void addTasksHandler(ActionEvent event)
    {
        if (!task_field.getText().trim().isEmpty() && task_field.getText().length() <= 26) {
            TaskController task = new TaskController(task_field.getText(), db.getMaxId(conn), conn, db);
            tasks.getChildren().add(task);
            db.insertTask(conn, task_field.getText());
            this.task_field.setText("");
        }
        else {
            if (task_field.getText().length() > 26) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task is too long!");
                alert.show();
            } else if (task_field.getText().trim().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Information message");
                alert.setContentText("Your task input field is empty!");
                alert.show();
            }
        }
    }
}