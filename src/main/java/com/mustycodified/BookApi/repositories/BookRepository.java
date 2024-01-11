package com.mustycodified.BookApi.repositories;

import com.mustycodified.BookApi.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
   Optional<BookEntity>  findByTitle(String title);
   boolean existsByAuthor(String author);
}
