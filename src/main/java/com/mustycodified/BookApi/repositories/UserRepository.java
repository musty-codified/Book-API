package com.mustycodified.BookApi.repositories;

import com.mustycodified.BookApi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional <User>findByUuid(String userId);
}
