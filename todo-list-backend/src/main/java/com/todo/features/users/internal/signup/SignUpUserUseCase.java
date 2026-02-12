package com.todo.features.users.internal.signup;

import com.todo.domain.entites.User;
import com.todo.domain.exceptions.ResourceDuplicatedException;
import com.todo.domain.repositories.UserRepository;
import com.todo.features.users.UserMapper;
import com.todo.features.users.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse execute(SignUpUserRequest signUpUserRequest) {
        boolean isEmailTaken = userRepository.existsByEmail(signUpUserRequest.email());

        if (isEmailTaken)
            throw new ResourceDuplicatedException("Email already exists");

        User user = User.builder()
                .name(signUpUserRequest.name())
                .email(signUpUserRequest.email())
                .password(signUpUserRequest.password()) //todo: Encrypt password
                .build();

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}
