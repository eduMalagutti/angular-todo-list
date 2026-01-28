package com.todo.domain.entites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PriorityEnum {
    UNSPECIFIED(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int value;

    PriorityEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static PriorityEnum fromValue(int value) {
        for (PriorityEnum priority : values()) {
            if (priority.value == value) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown priority value: " + value);
    }
}
