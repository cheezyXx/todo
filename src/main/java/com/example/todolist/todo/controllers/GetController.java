package com.example.todolist.todo.controllers;

import com.example.todolist.common.data.TodoResponse;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.todo.SortEnum;
import com.example.todolist.todo.services.ImageService;
import com.example.todolist.todo.services.TodoService;
import com.example.todolist.user.service.CustomUserDetailsService;
import com.example.todolist.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Tag(name="Todo")
@RestController
@RequestMapping("api/v1/")
public class GetController {

    private final TodoService todoService;

    private final ImageService imageService;

    private final CustomUserDetailsService customUserDetailsService;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @Autowired
    public GetController(
            TodoService todoService,
            ImageService imageService,
            CustomUserDetailsService customUserDetailsService,
            UserService userService,
            JwtUtils jwtUtils
        ) {

        this.imageService = imageService;
        this.todoService = todoService;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<TodoResponse> get(@PathVariable("id") UUID id) throws Exception {
        var todo = todoService.get(id);

        return ResponseEntity.ok().body(new TodoResponse(todo.getId(), todo.getLabel(), todo.getStatus()));
    }

    @GetMapping("/todos")
    public ResponseEntity<Object> getAdminAll(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("search") Optional<String> search,
            @RequestParam("sort") Optional<SortEnum> sort
    ) {
        var todos = todoService.getAll(
                page.orElse(0),
                size.orElse(10),
                sort.orElse(SortEnum.ASC),
                search.orElse("")
        );

        return ResponseEntity.ok().body(todos);
    }

    @GetMapping("/user/{userId}/todos")
    public ResponseEntity<Page<TodoResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @PathVariable("userId") UUID userId,
            @RequestHeader (name="Authorization") String token
    ) throws Exception {
        var user = userService.findById(userId);
        var userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        var valid = jwtUtils.validateToken(token.substring(7), userDetails);

        if (!valid) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var todos = todoService.getAllByUserId(page, size, userId);

        return ResponseEntity.ok().body(todos);
    }

    @GetMapping("/todo/{todoId}/image")
    public ResponseEntity<Resource> getImage(@PathVariable("todoId") UUID imageId) throws Exception {
        var image = imageService.get(imageId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }
}
