package com.todo.features.users.internal;

import com.todo.features.todolists.TodoListResponse;
import com.todo.features.users.UserResponse;
import com.todo.features.users.internal.signup.SignUpUserRequest;
import com.todo.features.users.internal.signup.SignUpUserUseCase;
import com.todo.features.users.internal.todolists.create.CreateTodoListForUserRequest;
import com.todo.features.users.internal.todolists.create.CreateTodoListForUserUseCase;
import com.todo.features.users.internal.todolists.getall.GetAllTodoListsForUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpUserUseCase signUpUserUseCase;
    private final GetAllTodoListsForUserUseCase getAllTodoListsForUserUseCase;
    private final CreateTodoListForUserUseCase createTodoListForUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        var userResponse = signUpUserUseCase.execute(signUpUserRequest);

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(userResponse);
    }

    @PostMapping("/{userId}/todolists")
    public ResponseEntity<TodoListResponse> createTodoList(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateTodoListForUserRequest request) {
        TodoListResponse todoListResponse = createTodoListForUserUseCase.execute(userId, request);

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todoListResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(todoListResponse);
    }

    @GetMapping("/{userId}/todolists")
    public ResponseEntity<List<TodoListResponse>> getAllTodoLists(@PathVariable UUID userId) {
        List<TodoListResponse> todoLists = getAllTodoListsForUserUseCase.execute(userId);
        return ResponseEntity.ok(todoLists);
    }
}
