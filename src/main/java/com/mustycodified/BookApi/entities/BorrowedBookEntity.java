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

    private String isbn;
    private String borrowerName;
    private boolean returned = false;
    @ManyToOne
    private BookEntity bookEntity;

    @ManyToOne
    private  UserEntity user;


}
