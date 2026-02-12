package com.todo.features.todos.internal.create;

import com.todo.domain.entites.PriorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTodoRequest(
        @NotBlank(message = "Task is required")
        @Size(max = 500, message = "Task must not exceed 500 characters")
        String task,

        @NotNull(message = "Priority is required")
        PriorityEnum priority,

        @NotNull(message = "TodoList ID is required")
        Long todoListId
) {}

