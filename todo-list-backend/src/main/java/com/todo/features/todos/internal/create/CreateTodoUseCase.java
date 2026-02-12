package com.todo.features.todos.internal.create;

import com.todo.domain.entites.Todo;
import com.todo.domain.repositories.TodoRepository;
import com.todo.features.todos.TodoMapper;
import com.todo.features.todos.TodoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTodoUseCase {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private static final PolicyFactory SANITIZER = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    public TodoResponse execute(CreateTodoRequest createTodoRequest) {
        // Prevent XSS attacks
        String sanitizedTask = SANITIZER.sanitize(createTodoRequest.task());

        Todo todo = Todo.builder()
                .task(sanitizedTask)
                .priority(createTodoRequest.priority())
                .build();

        Todo savedTodo = todoRepository.save(todo);
        return todoMapper.toDTO(savedTodo);
    }
}

