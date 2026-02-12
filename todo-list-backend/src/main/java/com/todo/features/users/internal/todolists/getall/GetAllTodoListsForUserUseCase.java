package com.todo.features.users.internal.todolists.getall;

import com.todo.domain.exceptions.ResourceNotFoundException;
import com.todo.domain.repositories.TodoListRepository;
import com.todo.domain.repositories.UserRepository;
import com.todo.features.todolists.TodoListMapper;
import com.todo.features.todolists.TodoListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllTodoListsForUserUseCase {

    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final TodoListMapper todoListMapper;

    @Transactional(readOnly = true)
    public List<TodoListResponse> execute(UUID userId) {
        // Verify user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user", "id", userId.toString());
        }

        return todoListRepository.findByUserId(userId)
                .stream()
                .map(todoListMapper::toDTO)
                .toList();
    }
}

