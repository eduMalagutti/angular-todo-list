package com.todo.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource, String key, String value) {
        super(String.format("%s not found with %s: '%s'", resource, key, value));
    }
}
