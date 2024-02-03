package com.mustycodified.BookApi.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "borrowed_books")
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowedBookEntity extends BaseEntity{

    private String borrowedBookStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity bookEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Transaction> transaction;


}
