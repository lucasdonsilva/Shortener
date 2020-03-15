package com.lucasdonsilva.shortener.exception;

public class NotFoundException extends Exception {

    public NotFoundException(String alias) {
        super("alias " + alias + " not found.");
    }
}
