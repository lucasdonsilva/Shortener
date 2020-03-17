package com.lucasdonsilva.shortener.exception;

public class InvalidUrlException extends Exception {

    public InvalidUrlException(String url) {
        super("url " + url + " is invalid.");
    }
}
