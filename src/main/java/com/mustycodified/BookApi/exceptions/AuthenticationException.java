package com.mustycodified.BookApi.exceptions;

public class AuthenticationException extends RuntimeException{
    private String debugMessage;
    public AuthenticationException() {
        super();
        this.debugMessage="Authentication failed";
    }
    public AuthenticationException(String message){
        super(message);
        this.debugMessage=message;
    }
}
