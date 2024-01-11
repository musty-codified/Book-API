package com.mustycodified.BookApi.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@Builder
public class BookEntity extends BaseEntity {


    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false)
    private String isbn;

    private boolean available = false;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    private UserEntity user;
}
