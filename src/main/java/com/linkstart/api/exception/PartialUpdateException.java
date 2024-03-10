package com.linkstart.api.exception;

public class PartialUpdateException extends RuntimeException {
    public PartialUpdateException(String message) {
        super(String.format("Error during PATCH: %s", message));
    }
}
