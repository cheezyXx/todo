package com.example.todolist.todo;

public enum SortEnum {
    ASC("ASC"),
    DESC("DESC");

    public final String name;

    private SortEnum(String name) {
        this.name = name;
    }
}
