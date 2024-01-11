package com.mustycodified.BookApi.services.impl;


import com.mustycodified.BookApi.dtos.requests.BookRequest;
import com.mustycodified.BookApi.dtos.response.BookResponse;
import com.mustycodified.BookApi.entities.BookEntity;
import com.mustycodified.BookApi.repositories.BookRepository;
import com.mustycodified.BookApi.services.BookService;
import com.mustycodified.BookApi.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AppUtils appUtil;

    @Override
    public List<BookResponse> getAllBooks() {
     List<BookEntity> books = bookRepository.findAll();
    List<BookResponse> bookResponses = books.stream()
            .map(bookEntity -> appUtil.getMapper().convertValue(bookEntity, BookResponse.class))
             .collect(Collectors.toList());

        return bookResponses;
    }

    @Override
    public BookResponse getBookById(Long id) {
        BookEntity book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        return appUtil.getMapper().convertValue(book, BookResponse.class);    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        if(bookRepository.existsByAuthor(bookRequest.getAuthor()))
            throw new RuntimeException("book already exists");

      BookEntity bookEntity = appUtil.getMapper().convertValue(bookRequest, BookEntity.class);

      bookEntity.setAvailable(true);
     BookEntity newBook = bookRepository.save(bookEntity);
     appUtil.print(newBook);
        return appUtil.getMapper().convertValue(newBook, BookResponse.class);
    }

    @Override
    public BookResponse editBook(Long id,  BookRequest updatedBook) {
      BookEntity bookEntity =  bookRepository.findById(id)
              .orElseThrow(()-> new RuntimeException("Book is not found"));
      bookEntity.setTitle(updatedBook.getTitle());
      bookEntity.setAuthor((updatedBook.getAuthor()));
      bookEntity.setAvailable(bookEntity.isAvailable());
        return appUtil.getMapper().convertValue(bookRepository.save(bookEntity), BookResponse.class);
    }

    @Override
    public void deleteBook(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Book not found"));
        bookRepository.delete(book);

    }

    @Override
    public void borrowBook(Long bookId, String borrowerName) {

    }

    @Override
    public void returnBorrowedBook(Long borrowedBookId) {

    }
}
