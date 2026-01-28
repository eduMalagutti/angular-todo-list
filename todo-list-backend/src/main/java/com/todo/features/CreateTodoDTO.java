package com.todo.features;

import com.todo.domain.entites.PriorityEnum;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

