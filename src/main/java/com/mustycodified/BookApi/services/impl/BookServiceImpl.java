package com.mustycodified.BookApi.services.impl;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.entities.BookEntity;
import com.mustycodified.BookApi.entities.BorrowedBookEntity;
import com.mustycodified.BookApi.entities.UserEntity;
import com.mustycodified.BookApi.enums.BookStatus;
import com.mustycodified.BookApi.enums.BorrowedBookStatus;
import com.mustycodified.BookApi.exceptions.NotFoundException;
import com.mustycodified.BookApi.exceptions.UnavailableException;
import com.mustycodified.BookApi.exceptions.ValidationException;
import com.mustycodified.BookApi.repositories.BookRepository;
import com.mustycodified.BookApi.repositories.BorrowedBookRepository;
import com.mustycodified.BookApi.repositories.UserRepository;
import com.mustycodified.BookApi.services.BookService;
import com.mustycodified.BookApi.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AppUtils appUtil;

    private final UserRepository userRepository;

    private final BorrowedBookRepository borrowedBookRepository;
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
        if(!book.getBorrowedBooks().isEmpty())
            throw new ValidationException("Book deletion failed as book is currently borrowed");
        bookRepository.delete(book);

    }

    @Override
    public BookResponseDto borrowBook(Long bookId, Long userId) {
       BookEntity bookEntity = bookRepository.findById(bookId)
               .orElseThrow(()-> new NotFoundException("Book not found"));


       if (bookEntity.getQuantity() <= 0)
           throw new UnavailableException("Book is not available at the moment");

       BorrowedBookEntity borrowedBook = createBorrowedBook(bookEntity, userId);
       borrowedBookRepository.save(borrowedBook);
       bookEntity.setQuantity(bookEntity.getQuantity() - 1);
      BookEntity updatedBookEntity = bookRepository.save(bookEntity);

        return appUtil.getMapper().convertValue(updatedBookEntity, BookResponseDto.class);
    }

    private BorrowedBookEntity createBorrowedBook(BookEntity book, Long userId) {
     UserEntity user = userRepository.findById(userId)
             .orElseThrow(()-> new NotFoundException("User not found"));
       return BorrowedBookEntity.builder()
                .user(user)
                .bookEntity(book)
                .borrowedBookStatus(BorrowedBookStatus.BORROWED.name())
                .build();

    }

    @Override
    public BookResponseDto returnBorrowedBook(Long borrowedBookId) {

         BorrowedBookEntity borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                 .orElseThrow(()->new NotFoundException("Borrowed book not found"));

      BookEntity bookEntity = borrowedBook.getBookEntity();
      bookEntity.setQuantity(bookEntity.getQuantity() + 1);
       BookEntity updatedBook = bookRepository.save(bookEntity);
      borrowedBookRepository.delete(borrowedBook);

        return appUtil.getMapper().convertValue(updatedBook, BookResponseDto.class);
    }
}
