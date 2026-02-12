package com.todo.features.users;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email
) {}
