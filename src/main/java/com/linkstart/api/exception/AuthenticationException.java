package com.linkstart.api.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(String.format("Error during authentication: %s", message));
    }
}
