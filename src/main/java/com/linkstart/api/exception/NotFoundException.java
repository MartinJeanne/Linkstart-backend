package com.linkstart.api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id, Class<?> c) {
        super(String.format("Not found object of: %s, with id: %s", c.getSimpleName(), id));
    }
}
