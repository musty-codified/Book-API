package com.mustycodified.BookApi.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.mustycodified.BookApi.enums.Authorities.*;


public enum Roles {
    ROLE_USER(Sets.newHashSet(USER_EDIT, BOOK_READ, BOOK_BORROW, BOOK_RETURN)),
    ROLE_AUTHOR(Sets.newHashSet(USER_READ, USER_EDIT, BOOK_READ, BOOK_EDIT));


    public final Set<Authorities> authorities;
    Roles(Set<Authorities> authorities) {
        this.authorities =authorities;
    }

    public Set<Authorities> getAuthorities(){
        return this.authorities;
    }
}
