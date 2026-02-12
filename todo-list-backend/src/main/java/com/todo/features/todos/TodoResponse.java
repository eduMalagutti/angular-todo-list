package com.todo.features.todos;

import com.todo.domain.entites.PriorityEnum;

public record TodoResponse(
        Long id,
        String task,
        PriorityEnum priority
) {
}

