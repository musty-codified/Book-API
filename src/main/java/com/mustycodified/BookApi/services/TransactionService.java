package com.mustycodified.BookApi.services;

import com.mustycodified.BookApi.dtos.response.BorrowedBookResponseDto;
import com.mustycodified.BookApi.entities.Book;


public interface TransactionService {
    BorrowedBookResponseDto createBorrowedBook(Book book, String email);
}
