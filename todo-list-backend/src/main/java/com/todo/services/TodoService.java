package com.todo.services;

import com.todo.exceptions.ResourceNotFoundException;
import com.todo.models.Todo;
import com.todo.repositories.TodoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Cacheable("todos")
    @Transactional(readOnly = true)
    public List<Todo> getAll() {
        return todoRepository.findAll(); // The Correct here would be pagination
    }

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    public void delete(Long id) {
        todoRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Todo", "id", id.toString()));
        todoRepository.deleteById(id);
    }
}
