package com.todo.features.users.internal.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpUserRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 500, message = "Task must not exceed 500 characters")
        String name,

        @NotBlank(message = "email is required")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "password is required")
        String password
) {
}
