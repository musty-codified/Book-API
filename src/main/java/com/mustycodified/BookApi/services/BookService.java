package com.mustycodified.BookApi.services;

import com.mustycodified.BookApi.dtos.requests.BookRequest;
import com.mustycodified.BookApi.dtos.response.BookResponse;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long id);
    BookResponse createBook(BookRequest bookRequest);
    BookResponse editBook(Long id, BookRequest updatedBook);
    void deleteBook(Long id);
    void borrowBook(String isbn, String borrowerName);
    void returnBorrowedBook(Long borrowedBookId);

}
