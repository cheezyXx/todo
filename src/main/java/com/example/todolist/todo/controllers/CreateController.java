package com.example.todolist.todo.controllers;

import com.example.todolist.common.data.TodoResponse;
import com.example.todolist.common.service.SecurityService;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.todo.data.ImageResponse;
import com.example.todolist.todo.entities.TodoEntity;
import com.example.todolist.todo.data.TodoRequest;
import com.example.todolist.todo.services.ImageService;
import com.example.todolist.todo.services.TodoService;
import com.example.todolist.user.service.CustomUserDetailsService;
import com.example.todolist.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Tag(name = "Todo")
@RestController
@RequestMapping("api/v1")
public class CreateController {

    private final TodoService todoService;

    private final ImageService imageService;

    private final SecurityService securityService;

    @Autowired
    public CreateController(
            TodoService todoService,
            ImageService imageService,
            SecurityService securityService
        ) {
        this.imageService = imageService;
        this.todoService = todoService;
        this.securityService = securityService;
    }

    @PostMapping(value = "/user/{userId}/todo", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<TodoResponse> create(
            @RequestBody @Valid TodoRequest todo,
            @PathVariable("userId") UUID userId,
            @RequestHeader (name="Authorization") String token
            ) throws Exception {
        securityService.hasAccess(token, userId);

        TodoEntity newTodo = todoService.create(todo, userId);
        URI url = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/user/{userId}/todo")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(url).body(new TodoResponse(newTodo.getId(), newTodo.getLabel(), newTodo.getStatus()));
    }

    @PostMapping("/todo/{todoId}/image")
    public ResponseEntity<Object> uploadImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("todoId") UUID todoId
    ) throws Exception {
        var image = imageService.uploadFile(file, todoId);

        String downloadURL = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/download")
                .path(image.getId().toString())
                .toUriString();

        return ResponseEntity.ok().body(new ImageResponse(
                downloadURL,
                image.getFileName(),
                file.getContentType(),
                file.getSize()
        ));
    }
}
