package com.todo.features.todolists.internal.delete;

import com.todo.domain.exceptions.ResourceNotFoundException;
import com.todo.domain.repositories.TodoListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteTodoListUseCase {

    private final TodoListRepository todoListRepository;

    @Transactional
    public void execute(Long todoListId) {
        if (!todoListRepository.existsById(todoListId)) {
            throw new ResourceNotFoundException("todoList", "id", todoListId.toString());
        }

        todoListRepository.deleteById(todoListId);
        log.info("TodoList with id {} deleted successfully", todoListId);
    }
}

