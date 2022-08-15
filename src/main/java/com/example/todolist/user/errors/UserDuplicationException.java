package com.example.todolist.user.errors;

public class UserDuplicationException extends Exception {
    public UserDuplicationException(String errorMessage) {
        super(errorMessage);
    }
}
