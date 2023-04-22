package com.linkstart.api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String content) {
        super(content + " not found.");
    }
}
