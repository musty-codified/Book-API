package com.mustycodified.BookApi.repositories;

import com.mustycodified.BookApi.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
   Optional<Book>  findByTitle(String title);
   boolean existsByTitle(String title);
   boolean existsByAuthorOrTitle(String author, String title);
}
