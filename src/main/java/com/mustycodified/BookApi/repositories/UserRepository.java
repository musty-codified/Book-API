package com.mustycodified.BookApi.repositories;

import com.mustycodified.BookApi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional <UserEntity>findByUuid(String userId);
}
