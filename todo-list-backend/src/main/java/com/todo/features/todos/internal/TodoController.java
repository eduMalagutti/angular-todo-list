package com.todo.features.todos.internal;

import com.todo.features.todos.TodoResponse;
import com.todo.features.todos.internal.create.CreateTodoRequest;
import com.todo.features.todos.internal.create.CreateTodoUseCase;
import com.todo.features.todos.internal.delete.DeleteTodoUseCase;
import com.todo.features.todos.internal.getall.GetAllTodosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final CreateTodoUseCase createTodoUseCase;
    private final GetAllTodosUseCase getAllTodosUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@Valid @RequestBody CreateTodoRequest createTodoRequest) {
        var todoResponse = createTodoUseCase.execute(createTodoRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todoResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(todoResponse);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAll() {
        return ResponseEntity.ok(getAllTodosUseCase.execute());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteTodoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}

