package com.linkstart.backend.controller;

import com.linkstart.backend.exception.NoColumnsException;
import com.linkstart.backend.exception.NoFilterGivenException;
import com.linkstart.backend.exception.NoContentException;
import com.linkstart.backend.model.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private Map<String, Object> errorBuilder(String errorMessage) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", errorMessage);
        body.put("timestamp", LocalDateTime.now());
        return body;
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<Object> handleNoUser(Exception ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.errorBuilder(ex.toString()));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, NoFilterGivenException.class})
    public ResponseEntity<Object> handleNoFilterGiven(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.errorBuilder(ex.toString()));
    }

    @ExceptionHandler(NoColumnsException.class)
    public ResponseEntity<Object> handleNoNoColumns(Exception ex) {
        Map<String, Object> body = this.errorBuilder(ex.toString());

        List<String> columns = new ArrayList<>();
        Field[] fields = Member.class.getDeclaredFields();
        for (Field field : fields) columns.add(field.getName());
        body.put("columnsList", columns);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
