package com.mustycodified.BookApi.repositories;

import com.mustycodified.BookApi.entities.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

}
