package com.mustycodified.BookApi.controllers;

import com.mustycodified.BookApi.dtos.requests.BookRequest;
import com.mustycodified.BookApi.dtos.response.ApiResponse;
import com.mustycodified.BookApi.dtos.response.BookResponse;
import com.mustycodified.BookApi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody BookRequest bookRequest){
      return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.createBook(bookRequest)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> editBook(@PathVariable Long id, @RequestBody BookRequest updateBook){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.editBook(id, updateBook)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable Long id){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.getBookById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBook(){
        return ResponseEntity.ok().body( new ApiResponse<>("Success", true, bookService.getAllBooks()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
