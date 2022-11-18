package com.linkstart.backend.exception;

public class NoColumnsException extends RuntimeException {
    public NoColumnsException(String wrongColumnName) {
        super("No column with name: " + wrongColumnName);
    }
}
