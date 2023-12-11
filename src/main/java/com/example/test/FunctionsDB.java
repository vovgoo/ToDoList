package com.example.test;

import java.sql.*;

public class FunctionsDB {
    public Connection connect_to_db(String dbname, String user, String password){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname,user,password);
            if(conn != null){
                System.out.println("Connection to database.");
            }
            else{
                System.out.println("Connection not to database.");
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return conn;
    }

    public void createTable(Connection conn){
        Statement statement;
        try {
            String query = "CREATE TABLE IF NOT EXISTS tasks (id SERIAL PRIMARY KEY, task VARCHAR(255));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table with tasks created!");
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void insertTask(Connection conn, String task){
        Statement statement;
        try{
            String query = "INSERT INTO tasks (task) VALUES ('" + task + "')";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data insert to tables.");
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public int getMaxId(Connection conn){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = "SELECT MAX(id) FROM tasks";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                return rs.getInt(1);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public String getNameById(Connection conn, int id){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = "SELECT task FROM tasks WHERE id = " + id;
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return "None";
    }
}
