package com.example.todolist.todo.repository;

import com.example.todolist.todo.TodoStatus;
import com.example.todolist.todo.entities.TodoEntity;
import com.example.todolist.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, UUID> {
    Page<TodoEntity> findAllByUserId(UserEntity userId, Pageable pageable);

    Page<TodoEntity> findByLabelContainingIgnoreCase(String label, Pageable pageable);
}
