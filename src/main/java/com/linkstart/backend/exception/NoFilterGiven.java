package com.linkstart.backend.exception;

public class NoFilterGiven extends RuntimeException {
    public NoFilterGiven() {
        super("Search is empty.");
    }
}
