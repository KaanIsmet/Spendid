package com.KaanIsmetOkul.Spendid.exceptionHandling;

public class BudgetNotFound extends RuntimeException {
    public BudgetNotFound(String message) {
        super(message);
    }
}
