package com.example.todolist.todo.services;

import com.example.todolist.common.data.TodoResponse;
import com.example.todolist.todo.SortEnum;
import com.example.todolist.todo.entities.TodoEntity;
import com.example.todolist.todo.TodoStatus;
import com.example.todolist.todo.data.TodoRequest;
import com.example.todolist.todo.repository.TodoRepository;
import com.example.todolist.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final UserService userService;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    public TodoEntity update(UUID id, TodoRequest todo) throws Exception {
        var currentTodo = get(id);

        BeanUtils.copyProperties(todo, currentTodo);
        return todoRepository.save(currentTodo);
    }

    public TodoEntity create(TodoRequest todo, UUID userId) throws Exception {
        var user = userService.findById(userId);

        var newTodo = new TodoEntity(todo.label(), user, TodoStatus.CREATED);

        return todoRepository.save(newTodo);
    }

    public TodoEntity get(UUID id) throws Exception {
        return todoRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Cannot find a todo [%s]", id)));
    }

    public Page<TodoResponse> getAllByUserId(Integer page, Integer size, UUID userId) throws Exception {
        var user = userService.findById(userId);
        var pagination = PageRequest.of(page, size);
        return todoRepository.findAllByUserId(user, pagination).map(todo -> {
            return new TodoResponse(todo.getId(), todo.getLabel(), todo.getStatus());
        });
    }

    public Page<TodoResponse> getAll(
            Integer page,
            Integer size,
            SortEnum sort,
            String search
        ) {
        var currentSort = sort == SortEnum.ASC ? Sort.by("label").ascending() : Sort.by("label").descending();
        var pagination = PageRequest.of(page, size, currentSort);

        if (!search.equals("")) {
            return todoRepository.findByLabelContainingIgnoreCase(search, pagination).map(todo -> {
                return new TodoResponse(todo.getId(), todo.getLabel(), todo.getStatus());
            });
        }

        return todoRepository.findAll(pagination).map(todo -> {
            return new TodoResponse(todo.getId(), todo.getLabel(), todo.getStatus());
        });
    }

    public void delete(UUID id) {
        todoRepository.deleteById(id);
    }
}
