package com.mustycodified.BookApi.services;

import com.mustycodified.BookApi.dtos.response.BorrowedBookResponseDto;
import com.mustycodified.BookApi.entities.BookEntity;


public interface TransactionService {
    BorrowedBookResponseDto createBorrowedBook(BookEntity book, String email);
}
