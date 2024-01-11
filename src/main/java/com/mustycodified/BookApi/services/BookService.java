package com.mustycodified.BookApi.services;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.entities.BookEntity;

import java.util.List;

public interface BookService {
    List<BookResponseDto> getAllBooks();
    BookResponseDto getBookById(Long id);
    BookResponseDto createBook(BookRequestDto bookRequest);
    BookResponseDto editBook(Long id, BookRequestDto updatedBook);
    void deleteBook(Long id);
    BookResponseDto borrowBook(Long bookId, Long userId);
//    BookResponseDto borrowBook(BookEntity book, Long userId);
    BookResponseDto returnBorrowedBook(Long borrowedBookId);

}
