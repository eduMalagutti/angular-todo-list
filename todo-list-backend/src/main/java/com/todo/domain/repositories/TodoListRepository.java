package com.todo.domain.repositories;

import com.todo.domain.entites.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    List<TodoList> findByUserId(UUID userId);
}
