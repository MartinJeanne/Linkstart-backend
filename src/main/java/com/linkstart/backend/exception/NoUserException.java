package com.linkstart.backend.exception;

public class NoUserException extends RuntimeException {
    public NoUserException() {
        super("No user in database.");
    }
}
