package com.example.todolist.common.data;

import javax.validation.constraints.NotEmpty;

public record RefreshTokenRequest(@NotEmpty  String refreshToken) {
}
