package com.linkstart.backend.exception;

public class NoContentException extends RuntimeException {
    public NoContentException(String content) {
        super("No " + content + " in database.");
    }
}
