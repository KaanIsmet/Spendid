package com.KaanIsmetOkul.Spendid.exceptionHandling;

public class ExpenseNotFound extends RuntimeException {
    public ExpenseNotFound(String message) {
        super(message);
    }
}
