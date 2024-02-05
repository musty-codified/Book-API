package com.mustycodified.BookApi.exceptions;

public class CustomAuthenticationException extends RuntimeException{
    private String debugMessage;
    public CustomAuthenticationException() {
        super();
        this.debugMessage="Authentication failed";
    }
    public CustomAuthenticationException(String message){
        super(message);
        this.debugMessage=message;
    }
}
