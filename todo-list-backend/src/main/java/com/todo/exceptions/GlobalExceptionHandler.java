package com.todo.exceptions;

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

        var error = new ErrorMessageDTO()
                .setTimestamp(Instant.now())
                .setException(e.getClass().getSimpleName())
                .setMessage(e.getMessage())
                .setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    public static class ErrorMessageDTO {
        private Instant timestamp;
        private String exception;
        private String message;
        private String path;

        public Instant getTimestamp() {
            return timestamp;
        }

        public ErrorMessageDTO setTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public String getException() {
            return exception;
        }

        public ErrorMessageDTO setException(String exception) {
            this.exception = exception;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public ErrorMessageDTO setMessage(String message) {
            this.message = message;
            return this;
        }

        public String getPath() {
            return path;
        }

        public ErrorMessageDTO setPath(String path) {
            this.path = path;
            return this;
        }
    }
}
