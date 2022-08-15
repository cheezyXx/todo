package com.example.todolist.todo.controllers;

import com.example.todolist.common.data.ErrorCodes;
import com.example.todolist.common.data.ErrorResponse;
import com.example.todolist.common.data.NoAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GetControllerExceptionHandler {

    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidAction(MissingServletRequestParameterException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                exception.getMessage(),
                ErrorCodes.BAD_PARAMETER,
                HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(value = { NoAccessException.class })
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleInvalidAction(NoAccessException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                exception.getMessage(),
                ErrorCodes.INVALID_ACTION,
                HttpStatus.FORBIDDEN.value()
        ));
    }

    @ExceptionHandler(value = { InvalidDataAccessApiUsageException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidParameter(InvalidDataAccessApiUsageException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                exception.getMessage(),
                ErrorCodes.BAD_PARAMETERS_OR_BODY,
                HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidParameter() {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                "Missing id",
                ErrorCodes.BAD_PARAMETER,
                HttpStatus.BAD_REQUEST.value()
        ));
    }
}
