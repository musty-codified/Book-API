package com.mustycodified.BookApi.services.impl;


import com.mustycodified.BookApi.dtos.response.BorrowedBookResponseDto;
import com.mustycodified.BookApi.entities.BookEntity;
import com.mustycodified.BookApi.entities.BorrowedBookEntity;
import com.mustycodified.BookApi.entities.Transaction;
import com.mustycodified.BookApi.entities.UserEntity;
import com.mustycodified.BookApi.enums.BorrowedBookStatus;
import com.mustycodified.BookApi.enums.TransactionStatus;
import com.mustycodified.BookApi.enums.TransactionType;
import com.mustycodified.BookApi.exceptions.NotFoundException;
import com.mustycodified.BookApi.exceptions.ValidationException;
import com.mustycodified.BookApi.repositories.TransactionRepository;
import com.mustycodified.BookApi.repositories.UserRepository;
import com.mustycodified.BookApi.services.TransactionService;
import com.mustycodified.BookApi.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AppUtils appUtil;

    @Override
    @Transactional
    public BorrowedBookResponseDto createBorrowedBook(BookEntity book, String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User not found"));

        BigDecimal bookPrice = calculateCharge(book);

        Transaction transaction = transactionRepository.findByEmail(email)
                .orElse(Transaction.builder()
                        .reference(appUtil.generateSerialNumber("TR"))
                        .amount(bookPrice)
                        .email(email)
                        .transactionType(TransactionType.TRANSACTION_TYPE_BORROW.getTransactionType())
                        .build());

        BigDecimal balance = userRepository.findByEmail(email)
                .map(UserEntity::getWalletBalance).orElse(BigDecimal.ZERO);

        if (balance.compareTo(bookPrice) < 0)
            throw new ValidationException("Insufficient fund");

        user.setWalletBalance(user.getWalletBalance().subtract(bookPrice));
        userRepository.save(user);
        transaction.setTransactionStatus(TransactionStatus.PAID.name());

        transactionRepository.save(transaction);

        BorrowedBookEntity borrowedBook = BorrowedBookEntity.builder()
                .bookEntity(book)
                .user(user)
                .borrowedBookStatus(BorrowedBookStatus.BORROWED.name())
                .build();

        return appUtil.getMapper().convertValue(borrowedBook, BorrowedBookResponseDto.class);
    }



    private BigDecimal calculateCharge(BookEntity book) {
        return book.getPrice().multiply(BigDecimal.valueOf(0.1));
    }
}
