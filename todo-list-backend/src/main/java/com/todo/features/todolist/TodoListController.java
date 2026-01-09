package com.todo.features.todolist;

import com.todo.domain.entites.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/todolists")
@RequiredArgsConstructor
public class TodoListController {

    private final TodoListService todoListService;

    @PostMapping
    public ResponseEntity<TodoList> create(@Valid @RequestBody CreateTodoListDTO createTodoListDTO) {
        var createdTodoList = todoListService.save(createTodoListDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTodoList.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdTodoList);
    }

    @GetMapping
    public ResponseEntity<List<TodoList>> getAll() {
        return ResponseEntity.ok(todoListService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoListService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
