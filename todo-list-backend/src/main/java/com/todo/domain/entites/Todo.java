package com.todo.domain.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
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

    @Column(columnDefinition = "VARCHAR(500)")
    private String task;

    @Enumerated(EnumType.ORDINAL)
    private PriorityEnum priority;

    @ManyToOne
    @JoinColumn(name = "todo_list_id")
    @JsonBackReference
    private TodoList todoList;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return Objects.equals(getId(), todo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
