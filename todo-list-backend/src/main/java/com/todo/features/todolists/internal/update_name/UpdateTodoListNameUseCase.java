package com.todo.features.todolists.internal.update_name;

import com.todo.domain.entites.TodoList;
import com.todo.domain.exceptions.ResourceNotFoundException;
import com.todo.domain.repositories.TodoListRepository;
import com.todo.features.todolists.TodoListMapper;
import com.todo.features.todolists.TodoListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateTodoListNameUseCase {

    private final TodoListRepository todoListRepository;
    private final TodoListMapper todoListMapper;

    @Transactional
    public TodoListResponse execute(Long todoListId, UpdateTodoListNameRequest request) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new ResourceNotFoundException("todoList", "id", todoListId.toString()));

        // Update only the name
        todoList.setName(request.name());

        TodoList savedTodoList = todoListRepository.save(todoList);
        log.info("TodoList with id {} name updated to '{}'", todoListId, request.name());
        return todoListMapper.toDTO(savedTodoList);
    }
}



