package com.mustycodified.BookApi.controllers;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.ApiResponse;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BookResponseDto>> createBook(@Valid @RequestBody BookRequestDto bookRequest){
      return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.createBook(bookRequest)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> editBook(@PathVariable Long id, @RequestBody BookRequestDto updateBook){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.editBook(id, updateBook)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> getBook(@PathVariable Long id){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.getBookById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getAllBook(){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.getAllBooks()));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/borrow/{bookId}/{email}")
    public ResponseEntity<ApiResponse<BookResponseDto>> borrowBook(@PathVariable Long bookId, @PathVariable String email){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.borrowBook(bookId, email)));
    }

    @PostMapping("/return/{borrowedBookId}")
    public ResponseEntity<ApiResponse<BookResponseDto>> returnBook( @PathVariable Long borrowedBookId){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.returnBorrowedBook(borrowedBookId)));
    }
}
