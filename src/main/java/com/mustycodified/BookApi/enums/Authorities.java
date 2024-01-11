package com.mustycodified.BookApi.enums;

public enum Authorities {
    USER_READ("user:read"),
    USER_EDIT("user:write"),
    BOOK_READ("book:read"),
    BOOK_EDIT("book:edit"),
    BOOK_BORROW("book:borrow"),
    BOOK_RETURN("book:return");

    private final String authorities;
    Authorities(String authorities){
        this.authorities = authorities;
    }
    public String getAuthorities() {
        return authorities;
    }
}
