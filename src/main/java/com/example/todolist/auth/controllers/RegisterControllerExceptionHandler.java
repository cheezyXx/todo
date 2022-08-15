package com.example.todolist.auth.controllers;

import com.example.todolist.common.data.ErrorCodes;
import com.example.todolist.common.data.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegisterControllerExceptionHandler {

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidParameter(MethodArgumentNotValidException exception) {
        var fieldError = exception.getBindingResult().getFieldError();
        var fieldValue = fieldError.getField();
        ErrorCodes errorCode = ErrorCodes.BAD_BODY;

        if (fieldValue.equals("password")) {
            errorCode = ErrorCodes.BAD_PASSWORD_LENGTH;
        } else if (fieldValue.equals("email")) {
            errorCode = ErrorCodes.INVALID_EMAIL;
        } else if (fieldValue.equals("firstName")) {
            errorCode = ErrorCodes.FIRST_NAME_LENGTH;
        } else if (fieldValue.equals("lastName")) {
            errorCode = ErrorCodes.LAST_NAME_LENGTH;
        } else if (fieldValue.equals("label")) {
            errorCode = ErrorCodes.WRONG_LABEL;
        }

        return ResponseEntity.badRequest().body(new ErrorResponse(
                fieldError.getDefaultMessage(),
                errorCode,
                HttpStatus.BAD_REQUEST.value()
        ));
    }
}
