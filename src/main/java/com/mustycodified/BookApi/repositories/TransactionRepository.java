package com.mustycodified.BookApi.repositories;

import com.mustycodified.BookApi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TransactionRepository extends JpaRepository<Transaction, String> {
    boolean existsByReferenceOrId(String reference, String id);

   Optional<Transaction> findByEmail(String email);
}
