package com.todo.features.todolists.internal;

import com.todo.features.todolists.TodoListResponse;
import com.todo.features.todolists.internal.delete.DeleteTodoListUseCase;
import com.todo.features.todolists.internal.update_name.UpdateTodoListNameRequest;
import com.todo.features.todolists.internal.update_name.UpdateTodoListNameUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/todo-lists")
@RequiredArgsConstructor
public class TodoListController {

    private final UpdateTodoListNameUseCase updateTodoListNameUseCase;
    private final DeleteTodoListUseCase deleteTodoListUseCase;

    @PatchMapping("/{todoListId}")
    public ResponseEntity<TodoListResponse> updateName(
            @PathVariable Long todoListId,
            @Valid @RequestBody UpdateTodoListNameRequest request) {
        TodoListResponse response = updateTodoListNameUseCase.execute(todoListId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{todoListId}")
    public ResponseEntity<Void> delete(@PathVariable Long todoListId) {
        deleteTodoListUseCase.execute(todoListId);
        return ResponseEntity.noContent().build();
    }
}
