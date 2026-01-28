package com.todo.features;

import com.todo.domain.entites.Todo;
import com.todo.domain.exceptions.ResourceNotFoundException;
import com.todo.domain.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private static final PolicyFactory SANITIZER = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    public Todo save(CreateTodoDTO createTodoDTO) {
        // Prevent XSS attacks
        String sanitizedTask = SANITIZER.sanitize(createTodoDTO.getTask());
        
        Todo todo = Todo.builder()
                .task(sanitizedTask)
                .priority(createTodoDTO.getPriority())
                .build();

        return todoRepository.save(todo);
    }

    @Cacheable("todos")
    @Transactional(readOnly = true)
    public List<Todo> getAll() {
        return todoRepository.findAll(); // The Correct here would be pagination
    }

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    public void delete(Long id) {
        if (!todoRepository.existsById(id))
            log.warn("TodoList not {} found", id);

        todoRepository.deleteById(id);
    }
}
