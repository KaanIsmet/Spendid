package com.KaanIsmetOkul.Spendid.exceptionHandling;

public class DuplicateBudgetException extends RuntimeException {
    public DuplicateBudgetException(String message) {
        super(message);
    }
}
