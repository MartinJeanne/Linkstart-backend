package com.linkstart.backend.controller;

import com.linkstart.backend.exception.NoFilterGiven;
import com.linkstart.backend.exception.NoUserException;
import com.linkstart.backend.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, NoUserException.class})
    public ResponseEntity<Object> handleNoUser(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, NoFilterGiven.class})
    public ResponseEntity<Object> handleNoFilterGiven(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
