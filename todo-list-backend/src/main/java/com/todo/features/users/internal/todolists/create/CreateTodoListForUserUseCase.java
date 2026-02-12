package com.todo.features.users.internal.todolists.create;

import com.todo.domain.entites.TodoList;
import com.todo.domain.entites.User;
import com.todo.domain.exceptions.ResourceNotFoundException;
import com.todo.domain.repositories.TodoListRepository;
import com.todo.domain.repositories.UserRepository;
import com.todo.features.todolists.TodoListMapper;
import com.todo.features.todolists.TodoListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateTodoListForUserUseCase {

    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final TodoListMapper todoListMapper;

    @Transactional
    public TodoListResponse execute(UUID userId, CreateTodoListForUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId.toString()));

        TodoList todoList = TodoList.builder()
                .name(request.name())
                .user(user)
                .build();

        TodoList savedTodoList = todoListRepository.save(todoList);
        return todoListMapper.toDTO(savedTodoList);
    }
}

