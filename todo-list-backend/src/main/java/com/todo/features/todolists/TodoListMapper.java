package com.todo.features.todolists;

import com.todo.domain.entites.TodoList;
import com.todo.features.todos.TodoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TodoMapper.class)
public interface TodoListMapper {

    @Mapping(target = "todos", source = "todos")
    TodoListResponse toDTO(TodoList todoList);
}

