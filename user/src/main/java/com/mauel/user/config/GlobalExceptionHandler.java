package com.mauel.user.config;

import com.mauel.user.exception.DuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> body = new HashMap<>();

        List<String> messages = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            messages.add(error.getDefaultMessage());
        }

        body.put("errors", messages);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleDuplicatedException(DuplicatedException exception){
        Map<String, String> body=new HashMap<>();
        body.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
