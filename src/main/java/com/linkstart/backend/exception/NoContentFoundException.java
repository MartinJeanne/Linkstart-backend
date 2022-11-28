package com.linkstart.backend.exception;

public class NoContentFoundException extends RuntimeException {
    public NoContentFoundException(String content, Long id) {
        super(content + " with id: " + id + ", not found.");
    }
}
