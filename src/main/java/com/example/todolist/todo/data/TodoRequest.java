package com.example.todolist.todo.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record TodoRequest(
        @NotEmpty(message = "Label is required")
        @Size(min = 2, max = 255, message = "Label must be between 2 and 255 characters long")
        String label,

        String deadline
) {
}
