package com.mustycodified.BookApi.services.impl;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.entities.BookEntity;
import com.mustycodified.BookApi.enums.BookStatus;
import com.mustycodified.BookApi.exceptions.NotFoundException;
import com.mustycodified.BookApi.exceptions.ValidationException;
import com.mustycodified.BookApi.repositories.BookRepository;
import com.mustycodified.BookApi.services.BookService;
import com.mustycodified.BookApi.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AppUtils appUtil;

    @Override
    public List<BookResponseDto> getAllBooks() {
     List<BookEntity> books = bookRepository.findAll();
     List<BookResponseDto> bookResponses = books.stream()
            .map(bookEntity -> appUtil.getMapper().convertValue(bookEntity, BookResponseDto.class))
             .collect(Collectors.toList());

        return bookResponses;
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        BookEntity book = bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found"));
        return appUtil.getMapper().convertValue(book, BookResponseDto.class);
    }

    @Override
    public BookResponseDto createBook(BookRequestDto bookRequest) {
        if(bookRepository.existsByTitle(bookRequest.getTitle()))
            throw new ValidationException("book already exists");

        BookEntity bookEntity = BookEntity.builder()
                .isbn(appUtil.generateSerialNumber("isbn"))
                .quantity(bookRequest.getQuantity())
                .bookStatus(BookStatus.AVAILABLE.name())
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .build();
           BookEntity newBook = bookRepository.save(bookEntity);
        return appUtil.getMapper().convertValue(newBook, BookResponseDto.class);
    }

    @Override
    public BookResponseDto editBook(Long id, BookRequestDto updatedBook) {
      BookEntity bookEntity = bookRepository.findById(id)
              .orElseThrow(()-> new NotFoundException("Book is not found"));
        bookEntity.setAuthor(updatedBook.getAuthor());
        bookEntity.setTitle(updatedBook.getTitle());
        bookEntity.setQuantity(updatedBook.getQuantity());
        bookEntity.setBookStatus(BookStatus.AVAILABLE.name());

        bookEntity = bookRepository.save(bookEntity);

        return appUtil.getMapper().convertValue(bookEntity, BookResponseDto.class);
    }

    @Override
    public void deleteBook(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Book not found"));
        bookRepository.delete(book);

    }

    @Override
    public void borrowBook(String isbn, String borrowerName) {

    }

    @Override
    public void returnBorrowedBook(Long borrowedBookId) {

    }
}
