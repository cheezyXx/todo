package com.example.todolist.todo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<TodoStatus, String> {
    @Override
    public String convertToDatabaseColumn(TodoStatus status) {
        if (status == null) {
            return null;
        }
        return status.toString();
    }

    @Override
    public TodoStatus convertToEntityAttribute(String string) {
        if (string == null) {
            return null;
        }
        try {
            return TodoStatus.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
