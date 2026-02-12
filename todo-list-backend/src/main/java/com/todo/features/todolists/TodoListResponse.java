package com.todo.features.todolists;

import com.todo.features.todos.TodoResponse;

import java.util.List;

public record TodoListResponse(
        Long id,
        String name,
        List<TodoResponse> todos
) {
}

