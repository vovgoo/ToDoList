package com.example.test;

public class Task {
    private final String name;
    private final int id;

    public Task(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
