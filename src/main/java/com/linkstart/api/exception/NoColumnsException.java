package com.linkstart.api.exception;

public class NoColumnsException extends RuntimeException {
    public NoColumnsException(String wrongColumnName) {
        super("No column with name: " + wrongColumnName);
    }
}
