package com.mustycodified.BookApi.exceptions;

public class UnavailableException extends RuntimeException{

    private String message;

    public UnavailableException() {
        super();
        this.message = "Resource is unavailable";
    }

    public UnavailableException(String message) {
        super(message);
        this.message = message;
    }
}
