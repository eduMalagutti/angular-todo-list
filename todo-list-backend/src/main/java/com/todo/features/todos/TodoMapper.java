package com.todo.features.todos;

import com.todo.domain.entites.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoMapper {

    TodoResponse toDTO(Todo todo);
}

