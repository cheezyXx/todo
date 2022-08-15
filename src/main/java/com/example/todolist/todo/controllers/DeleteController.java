package com.example.todolist.todo.controllers;

import com.example.todolist.todo.services.TodoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name="Todo")
@RestController
@RequestMapping("api/v1")
public class DeleteController {

    private final TodoService todoService;

    @Autowired
    public DeleteController(TodoService todoService) {
        this.todoService = todoService;
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        todoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
