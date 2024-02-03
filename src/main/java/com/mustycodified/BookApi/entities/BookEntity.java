package com.mustycodified.BookApi.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String bookStatus;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;


    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<BorrowedBookEntity> borrowedBooks;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
