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
@Table(name = "borrowed_books")
@Builder
public class BorrowedBookEntity extends BaseEntity{


    private String borrowerName;

    private String borrowedBookStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity bookEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;


}
