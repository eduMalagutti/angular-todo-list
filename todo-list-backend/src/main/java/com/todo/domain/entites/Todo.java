package com.todo.domain.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private TodoList todoList;

    @Column(columnDefinition = "VARCHAR(500)")
    private String task;

    @Enumerated(EnumType.ORDINAL)
    private PriorityEnum priority;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Todo todo)) return false;
        return Objects.equals(getId(), todo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
