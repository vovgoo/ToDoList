module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    exports com.example.test.model;
    opens com.example.test.model to javafx.fxml;
    exports com.example.test.db;
    opens com.example.test.db to javafx.fxml;
    exports com.example.test.controllers;
    opens com.example.test.controllers to javafx.fxml;
    exports com.example.test.application;
    opens com.example.test.application to javafx.fxml;
}