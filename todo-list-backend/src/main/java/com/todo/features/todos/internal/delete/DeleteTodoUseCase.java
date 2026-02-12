package com.todo.features.todos.internal.delete;

import com.todo.domain.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteTodoUseCase {

    private final TodoRepository todoRepository;

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    public void execute(Long id) {
        if (!todoRepository.existsById(id))
            log.warn("TodoList not {} found", id);

        todoRepository.deleteById(id);
    }
}

