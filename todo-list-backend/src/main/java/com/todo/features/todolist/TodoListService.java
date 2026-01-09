package com.todo.features.todolist;

import com.todo.domain.entites.TodoList;
import com.todo.domain.repositories.TodoListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoListService {

    private final TodoListRepository todoListRepository;
    private static final PolicyFactory SANITIZER = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);

    public TodoList save(CreateTodoListDTO createTodoListDTO) {
        // Sanitize the name to prevent XSS attacks
        String sanitizedName = SANITIZER.sanitize(createTodoListDTO.getName());

        TodoList todoList = TodoList.builder()
                .name(sanitizedName)
                .build();

        return todoListRepository.save(todoList);
    }

    public List<TodoList> getAll() {
        return todoListRepository.findAll();
    }

    public void delete(Long id) {
        if (!todoListRepository.existsById(id))
            log.warn("TodoList {} not found", id);

        todoListRepository.deleteById(id);
    }
}
