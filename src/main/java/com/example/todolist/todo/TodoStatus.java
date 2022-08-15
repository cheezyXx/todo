package com.example.todolist.todo;

public enum TodoStatus {
    CREATED("CREATED"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    public final String name;

    private TodoStatus(String name) {
        this.name = name;
    }
}
