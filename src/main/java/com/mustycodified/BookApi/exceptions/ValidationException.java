package com.mustycodified.BookApi.exceptions;

public class ValidationException extends RuntimeException{
    private String debugMessage;
    public ValidationException(String message){
        super(message);
//        this.debugMessage = "Validation failed";
    }

    public ValidationException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
