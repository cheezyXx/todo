package com.example.todolist.common.data;

import com.example.todolist.user.reponse.UserResponse;

public record JwtResponse(String jwtToken, String refreshToken, UserResponse user) {
}
