package com.example.todolist.user.reponse;

import com.example.todolist.user.RoleEntity;

import java.util.Set;
import java.util.UUID;

public record UserResponse(String email, String firstname, String lastName, UUID id, Set<RoleEntity> roles) {
}
