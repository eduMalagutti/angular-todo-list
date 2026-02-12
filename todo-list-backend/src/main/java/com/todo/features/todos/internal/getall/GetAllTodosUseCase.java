package com.todo.features.todos.internal.getall;

import com.todo.domain.repositories.TodoRepository;
import com.todo.features.todos.TodoMapper;
import com.todo.features.todos.TodoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllTodosUseCase {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Cacheable("todos")
    @Transactional(readOnly = true)
    public List<TodoResponse> execute() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::toDTO)
                .toList(); // The Correct here would be pagination
    }
}

