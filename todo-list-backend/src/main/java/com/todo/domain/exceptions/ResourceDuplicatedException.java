package com.todo.domain.exceptions;

public class ResourceDuplicatedException extends RuntimeException{
    public ResourceDuplicatedException(String message) {
        super(message);
    }
}
