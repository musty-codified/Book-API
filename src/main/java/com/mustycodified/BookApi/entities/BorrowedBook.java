package com.mustycodified.BookApi.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowedBook extends BaseEntity{

    private String borrowedBookStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book bookEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Transaction transaction;


}
