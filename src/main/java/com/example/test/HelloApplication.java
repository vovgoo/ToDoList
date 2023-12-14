package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    public static DatabaseHandler database;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "javafx_todo", "postgres", "postgres");
            database = new DatabaseHandler(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setTitle("To Do List in Java!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}