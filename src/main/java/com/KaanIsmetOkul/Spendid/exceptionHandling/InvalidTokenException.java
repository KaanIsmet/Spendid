package com.KaanIsmetOkul.Spendid.exceptionHandling;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
