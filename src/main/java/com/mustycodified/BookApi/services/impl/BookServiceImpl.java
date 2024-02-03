package com.mustycodified.BookApi.services.impl;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.dtos.response.BorrowedBookResponseDto;
import com.mustycodified.BookApi.entities.Book;
import com.mustycodified.BookApi.entities.BorrowedBook;
import com.mustycodified.BookApi.enums.BookStatus;
import com.mustycodified.BookApi.exceptions.NotFoundException;
import com.mustycodified.BookApi.exceptions.UnavailableException;
import com.mustycodified.BookApi.exceptions.ValidationException;
import com.mustycodified.BookApi.repositories.BookRepository;
import com.mustycodified.BookApi.repositories.BorrowedBookRepository;
import com.mustycodified.BookApi.repositories.TransactionRepository;
import com.mustycodified.BookApi.repositories.UserRepository;
import com.mustycodified.BookApi.services.BookService;
import com.mustycodified.BookApi.services.TransactionService;
import com.mustycodified.BookApi.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AppUtils appUtil;

    private final UserRepository userRepository;

    private final BorrowedBookRepository borrowedBookRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionService transactionService;
    @Override
    public List<BookResponseDto> getAllBooks() {
     List<Book> books = bookRepository.findAll();
     List<BookResponseDto> bookResponses = books.stream()
            .map(bookEntity -> appUtil.getMapper().convertValue(bookEntity, BookResponseDto.class))
             .collect(Collectors.toList());

        return bookResponses;
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found"));
        return appUtil.getMapper().convertValue(book, BookResponseDto.class);
    }

    @Override
    public BookResponseDto createBook(BookRequestDto bookRequest) {
        if(bookRepository.existsByTitle(bookRequest.getTitle()))
            throw new ValidationException("book already exists");

        Book bookEntity = Book.builder()
                .isbn(appUtil.generateSerialNumber("isbn"))
                .quantity(bookRequest.getQuantity())
                .bookStatus(BookStatus.AVAILABLE.name())
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .price(bookRequest.getPrice())
                .build();
           Book newBook = bookRepository.save(bookEntity);
        return appUtil.getMapper().convertValue(newBook, BookResponseDto.class);
    }

    @Override
    public BookResponseDto editBook(Long id, BookRequestDto updatedBook) {
      Book bookEntity = bookRepository.findById(id)
              .orElseThrow(()-> new NotFoundException("Book is not found"));
        bookEntity.setAuthor(updatedBook.getAuthor());
        bookEntity.setTitle(updatedBook.getTitle());
        bookEntity.setQuantity(updatedBook.getQuantity());
        bookEntity.setBookStatus(BookStatus.AVAILABLE.name());
        bookEntity.setPrice(updatedBook.getPrice());

        bookEntity = bookRepository.save(bookEntity);

        return appUtil.getMapper().convertValue(bookEntity, BookResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Book not found"));
        if(!book.getBorrowedBooks().isEmpty())
            throw new ValidationException("Book deletion failed as book is currently borrowed");
        bookRepository.delete(book);

    }

    @Override
    public BookResponseDto borrowBook(Long bookId, String email) {
       Book bookEntity = bookRepository.findById(bookId)
               .orElseThrow(()-> new NotFoundException("Book not found"));

       if (bookEntity.getQuantity() <= 0)
           throw new UnavailableException("Book is not available at the moment");
//        bookEntity.setBookStatus(BookStatus.UNAVAILABLE.name());

        BorrowedBookResponseDto borrowedBook = transactionService.createBorrowedBook(bookEntity, email);
        BorrowedBook book = appUtil.getMapper().convertValue(borrowedBook, BorrowedBook.class);

       borrowedBookRepository.save(book);
       bookEntity.setQuantity(bookEntity.getQuantity() - 1);
      Book updatedBookEntity = bookRepository.save(bookEntity);

        return appUtil.getMapper().convertValue(updatedBookEntity, BookResponseDto.class);
    }

    @Override
    @Transactional
    public BookResponseDto returnBorrowedBook(Long borrowedBookId) {

         BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                 .orElseThrow(()->new NotFoundException("Borrowed book not found"));

      Book bookEntity = borrowedBook.getBookEntity();
      bookEntity.setQuantity(bookEntity.getQuantity() + 1);
       Book updatedBook = bookRepository.save(bookEntity);
      borrowedBookRepository.delete(borrowedBook);

        return appUtil.getMapper().convertValue(updatedBook, BookResponseDto.class);
    }
}
