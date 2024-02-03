package com.mustycodified.BookApi.enums;

public enum TransactionType {

    TRANSACTION_TYPE_BORROW("borrow"),
    TRANSACTION_TYPE_RETURN("return");


    private final String transactionType;
    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
