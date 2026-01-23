package com.todo.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleResourceNotFoundException(
            HttpServletRequest request,
            ResourceNotFoundException e) {

        var error = ErrorMessageDTO.builder()
                .timestamp(Instant.now())
                .exception(e.getClass().getSimpleName())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @Getter
    @Builder
    public static class ErrorMessageDTO {
        private Instant timestamp;
        private String exception;
        private String message;
        private String path;
    }
}
