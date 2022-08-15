package com.example.todolist.todo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name="Todo")
@RestController
@RequestMapping("api/v1")
public class UpdateController {

    @PatchMapping("/todo/{id}")
    public ResponseEntity update(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().build();
    }
}
