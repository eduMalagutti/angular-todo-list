package com.todo.domain.repositories;

import com.todo.domain.entites.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
