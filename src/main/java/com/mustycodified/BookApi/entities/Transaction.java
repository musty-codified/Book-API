package com.mustycodified.BookApi.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")

public class Transaction extends BaseEntity{

    private String reference;

    private String transactionType;

    private String transactionStatus;

    private String email;

    private BigDecimal amount;

    @ManyToOne
    private BorrowedBookEntity borrowedBook;

}
