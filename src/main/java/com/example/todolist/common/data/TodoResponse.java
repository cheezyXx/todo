package com.example.todolist.common.data;

import com.example.todolist.todo.TodoStatus;

import java.util.UUID;

public record TodoResponse(UUID id, String label, TodoStatus status) {
}
