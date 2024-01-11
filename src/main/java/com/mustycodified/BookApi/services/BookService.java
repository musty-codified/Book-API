package com.mustycodified.BookApi.services;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;

import java.util.List;

public interface BookService {
    List<BookResponseDto> getAllBooks();
    BookResponseDto getBookById(Long id);
    BookResponseDto createBook(BookRequestDto bookRequest);
    BookResponseDto editBook(Long id, BookRequestDto updatedBook);
    void deleteBook(Long id);
    BookResponseDto borrowBook(Long bookId, Long userId);
    BookResponseDto returnBorrowedBook(Long borrowedBookId);

}
