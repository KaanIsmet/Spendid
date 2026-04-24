package com.KaanIsmetOkul.Spendid.exceptionHandling;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
