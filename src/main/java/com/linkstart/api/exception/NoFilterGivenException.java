package com.linkstart.api.exception;

public class NoFilterGivenException extends RuntimeException {
    public NoFilterGivenException() {
        super("Search is empty.");
    }
}
