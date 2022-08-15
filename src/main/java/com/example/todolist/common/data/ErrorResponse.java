package com.example.todolist.common.data;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, ErrorCodes code, Integer status) {
}
