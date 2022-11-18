package com.linkstart.backend.exception;

public class NoFilterGivenException extends RuntimeException {
    public NoFilterGivenException() {
        super("Search is empty.");
    }
}
