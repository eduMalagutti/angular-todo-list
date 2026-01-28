package com.todo.features;

import com.todo.domain.entites.PriorityEnum;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateTodoDTO {

    @NotBlank(message = "Task is required")
    @Size(max = 500, message = "Task must not exceed 500 characters")
    private String task;

    @NotNull(message = "Priority is required")
    private PriorityEnum priority;

    @NotNull(message = "TodoList ID is required")
    private Long todoListId;
}

