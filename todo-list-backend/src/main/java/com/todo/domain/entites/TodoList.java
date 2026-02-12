package com.todo.domain.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    private final List<Todo> todos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TodoList todoList)) return false;
        return Objects.equals(getId(), todoList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
