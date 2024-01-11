package com.mustycodified.BookApi.repositories;

import com.mustycodified.BookApi.entities.BorrowedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBookEntity, Long> {
}
