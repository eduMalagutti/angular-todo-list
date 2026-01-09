package com.todo.domain.entites;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Todo> todos;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TodoList)) return false;
        TodoList todoList = (TodoList) o;
        return Objects.equals(getId(), todoList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
