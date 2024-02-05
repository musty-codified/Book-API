package com.mustycodified.BookApi.exceptions;

public class DuplicateException extends RuntimeException{
    private String debugMessage;

    public DuplicateException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }

    public DuplicateException(String message) {
        super(message);
    }
}
